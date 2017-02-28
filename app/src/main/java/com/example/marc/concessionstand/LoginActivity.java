package com.example.marc.concessionstand;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.sql.SQLException;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    String next_screen  = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText email_edit_text = (EditText)findViewById(R.id.email_edit_text);
        EditText user_edit_text = (EditText)findViewById(R.id.username_edit_text);
        EditText pw_edit_text = (EditText)findViewById(R.id.password_edit_text);

        email_edit_text.setText("Email");
        user_edit_text.setText("Name");
        pw_edit_text.setText("Password");

        Button login_button = (Button)findViewById(R.id.login_btn);
        login_button.setOnClickListener(this);
    }


    public void onClick(View v) {
        if (v.getId() == R.id.login_btn) {
            boolean valid_login = false;

            EditText email_edit_text = (EditText)findViewById(R.id.email_edit_text);
            EditText user_edit_text = (EditText)findViewById(R.id.username_edit_text);
            EditText pw_edit_text = (EditText)findViewById(R.id.password_edit_text);

            String email = email_edit_text.getText().toString();
            String user = user_edit_text.getText().toString();
            String pw = pw_edit_text.getText().toString();

            try {
                MySQLDatabase db = new MySQLDatabase();

                if (db.VerifyUser(email, pw)) {
                    valid_login = true;
                }
            }
            catch (Exception e){
                e.printStackTrace();
                System.out.println(e.getMessage());
            }

            if (valid_login) {

                email_edit_text.setText("Email");
                user_edit_text.setText("Name");
                pw_edit_text.setText("Password");

                String activity = "com.example.marc.concessionstand.WorkerMenu";
                Intent i = new Intent(activity);
                i.putExtra("Name" , user);
                startActivity(i);
            }
            else {
                TextView status = (TextView)findViewById(R.id.login_status_text);
                status.setText("Invalid Login Attempt");
            }
        }
    }

}
