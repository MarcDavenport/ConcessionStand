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

        Intent i = getIntent();
        next_screen = i.getStringExtra("NextScreen");
        System.out.println("Next activity will be " + next_screen);

        Button login_button = (Button)findViewById(R.id.login_btn);
        login_button.setOnClickListener(this);
    }


    public void onClick(View v) {
        if (v.getId() == R.id.login_btn) {
            boolean valid_login = false;

            EditText user_edit_text = (EditText)findViewById(R.id.username_edit_text);
            EditText pw_edit_text = (EditText)findViewById(R.id.password_edit_text);
            EditText email_edit_text = (EditText)findViewById(R.id.email_edit_text);

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
                String activity = "com.example.marc.concessionstand.WorkerMenu";
                Intent i = new Intent(activity);
                startActivity(i);
            }
            else {
                TextView status = (TextView)findViewById(R.id.login_status_text);
                status.setText("Invalid Login Attempt");
            }
        }
    }

}
