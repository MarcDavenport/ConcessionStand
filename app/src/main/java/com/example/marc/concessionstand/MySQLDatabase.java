package com.example.marc.concessionstand;

import android.os.StrictMode;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Marc on 2/27/2017.
 */

public class MySQLDatabase {


    protected Connection connection;
    protected Statement stmt;
    protected Statement stmt2;
    protected ResultSet results;

    MySQLDatabase() throws ClassNotFoundException {
        SetupDatabase();
    }

    protected void SetupDatabase() throws ClassNotFoundException {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        // This will load the MySQL driver, each DB has its own driver
        Class.forName("com.mysql.jdbc.Driver");
    }

    protected void OpenDBConnection() throws SQLException {
        /// jdbc:mysql://hostname:port/databasename
///        connection = DriverManager.getConnection("jdbc:mysql://107.180.47.119:3306/QuickJobDatabase",
///                "QuickJobUser", "QJUuosibe066#");

        connection = DriverManager.getConnection(
                "jdbc:mysql://107.180.47.119:3306/ConcessionStand?useUnicode=true&characterEncoding=utf-8" ,
                "ConcessionWorker" , "CWoonrck066#");

        System.out.println("Connected to database.");
        stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        stmt2 = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        System.out.println("Created statement.");
    }
    protected void CloseDBConnection() throws SQLException {
        connection.close();
    }

    public void RemoveUser(String email) throws SQLException , Exception {
        OpenDBConnection();
        if (1 != stmt.executeUpdate("DELETE FROM User WHERE UserEmail='" + email + "';")) {
            throw new Exception("Failed to delete user " + email + " from database!");
        }
        CloseDBConnection();
    }

    public void AddUser(String email , String name , String pword) throws SQLException , Exception {
        OpenDBConnection();
        results = stmt.executeQuery("SELECT * FROM User WHERE UserEmail='" + email + "'");
        if (results.next()) {
            throw new Exception("User already in database!");
        }
        else {
            if (1 != stmt.executeUpdate("INSERT INTO User VALUES(NULL , '" + email + "' , '" + name + "' , '" + pword + "');")) {
                throw new Exception("Could not insert user " + email + " into database");
            }
        }
        CloseDBConnection();
    }

    public boolean VerifyUser(String email , String password) throws SQLException {
        boolean valid = false;
        OpenDBConnection();

        String sql = "SELECT * FROM User WHERE UserEmail = '" + email + "';";
        results = stmt.executeQuery(sql);

        if (!results.next()) {
            valid = false;
        }
        else {
            if (results.getString(4).equals(password)) {
                valid = true;
            }
        }

        CloseDBConnection();

        return valid;
    }

}
