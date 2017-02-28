package com.example.marc.concessionstand;

import android.os.StrictMode;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by Marc on 2/27/2017.
 */

public class MySQLDatabase {

    /* CLASS TODO : Sanitize strings before adding them to the database */
    /* CLASS TODO : Catch errors and close database connection in finally blocks??? */

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

    public boolean HasUser(String email) throws Exception {
        boolean has_user = false;
        OpenDBConnection();
        results = stmt.executeQuery("SELECT * FROM User WHERE UserEmail='" + email + "'");
        if (results.next()) {
            has_user = true;
        }
        CloseDBConnection();
        return has_user;
    }

    public void RemoveUser(String email) throws SQLException , Exception {
        OpenDBConnection();
        if (1 != stmt.executeUpdate("DELETE FROM User WHERE UserEmail='" + email + "';")) {
            throw new Exception("Failed to delete user " + email + " from database!");
        }
        System.out.println("Removed user by email " + email + " to database.");
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
            System.out.println("Added user " + name + " to database.");
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
            System.out.println("User " + email + " not verified");
        }
        else {
            if (results.getString(4).equals(password)) {
                valid = true;
                System.out.println("Verified User");
            }
        }

        CloseDBConnection();

        return valid;
    }

    public InventoryEntry GetInventoryEntryFromResults(ResultSet rs) throws SQLException {

        InventoryEntry entry = new InventoryEntry();

        entry.SetFields(
                rs.getInt(1),
                rs.getString(2),
                rs.getBigDecimal(3),
                rs.getInt(4),
                ((rs.getInt(5) == 0)?false:true)
        );

        return entry;
    }

    public ArrayList<InventoryEntry> GetInventoryFromDatabase() throws SQLException {

        ArrayList<InventoryEntry> inventory = new ArrayList<InventoryEntry>();

        OpenDBConnection();

        String sql = "SELECT * FROM Inventory";
        results = stmt.executeQuery(sql);

        while (results.next()) {
            inventory.add(GetInventoryEntryFromResults(results));
        }

        CloseDBConnection();

        return inventory;
    }

    public void RemoveItemFromInventory(String name) throws Exception {
        OpenDBConnection();

        String sql = "DELETE FROM Inventory WHERE ItemName = '" + name + "';";
        int ret = stmt.executeUpdate(sql);
        if (1 != ret) {
            if (ret > 1) {
                throw new Exception("Deleted more than 1 (" + Integer.toString(ret) + "row from Inventory table!");
            }
            else {
                throw new Exception("Failed to delete item " + name + " from Inventory table!");
            }
        }

        CloseDBConnection();
    }

    public void AddItemToInventory(InventoryEntry entry) throws Exception {
        OpenDBConnection();

        String sql = "SELECT * FROM Inventory WHERE ItemName = '" + entry.item_name + "';";
        results = stmt.executeQuery(sql);

        if (results.next()) {
            /// Item already in inventory, add new quantity to quantity in stock and update price and for sale status
            InventoryEntry existing_entry = GetInventoryEntryFromResults(results);
            existing_entry.item_quantity += entry.item_quantity;
            existing_entry.item_price = entry.item_price;
            existing_entry.item_for_sale = entry.item_for_sale;

            CloseDBConnection();
            RemoveItemFromInventory(entry.item_name);
            AddItemToInventory(existing_entry);
            return;
        }

        String sql2 = "INSERT INTO Inventory VALUES(NULL , '" + entry.item_name + "' , '" + entry.item_price.toString() + "' , " +
                        Integer.toString(entry.item_quantity) + " , " + (entry.item_for_sale?"1":"0") + ";";
        if (1 != stmt2.executeUpdate(sql2)) {
            throw new Exception("Failed to insert entry into Inventory table!" + entry.toString());
        }

        CloseDBConnection();
    }

    public void UpdateItemInInventory(InventoryEntry entry) throws Exception {
        OpenDBConnection();

        String sql = "SELECT * FROM Inventory WHERE ItemName = '" + entry.item_name + "';";
        results = stmt.executeQuery(sql);
        int count = 0;
        InventoryEntry existing_entry = null;
        while (results.next()) {
            ++count;
            if (count == 1) {
                existing_entry = GetInventoryEntryFromResults(results);
            }
        }

        if (existing_entry != null) {
            /*
            UPDATE [LOW_PRIORITY] [IGNORE] table_reference
                SET col_name1={expr1|DEFAULT} [, col_name2={expr2|DEFAULT}] ...
                [WHERE where_condition]
                [ORDER BY ...]
                [LIMIT row_count]
             */

            entry.item_id = existing_entry.item_id;

            BigDecimal new_price = Rounder.RoundBigDecimalToNearestQuarter(new BigDecimal(entry.item_price.toString()));

            String sql2 = "UPDATE Inventory SET ItemQuantity = " + Integer.toString(entry.item_quantity) + " , ItemPrice = " +
                            new_price + " , ItemForSale = " + (entry.item_for_sale?1:0) + " WHERE ItemID = " + entry.item_id + ";";
            if (1 != stmt2.executeUpdate(sql2)) {
                throw new Exception("Failed to update Inventory item " + entry.toString());
            }
        }
        else {
            CloseDBConnection();
            AddItemToInventory(entry);
            return;
        }

        CloseDBConnection();
    }

}
