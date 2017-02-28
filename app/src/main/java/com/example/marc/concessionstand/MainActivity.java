package com.example.marc.concessionstand;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.app_bar_main);
        setSupportActionBar(myToolbar);

        Button b1 = (Button)findViewById(R.id.main_place_order_btn);
        b1.setOnClickListener(this);
        Button b2 = (Button)findViewById(R.id.main_worker_login_btn);
        b2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.main_place_order_btn || v.getId() == R.id.confirm_screen_menu_item) {
            Intent i = new Intent("com.example.marc.concessionstand.OrderScreen");
            startActivity(i);
        }
        if (v.getId() == R.id.main_worker_login_btn) {
            Intent i = new Intent("com.example.marc.concessionstand.LoginActivity");
            startActivity(i);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.screen_menu, menu);
        return true;
    }}
