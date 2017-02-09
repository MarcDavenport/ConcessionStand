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
        Button b = (Button)findViewById(R.id.submit_btn);
        b.setOnClickListener(this);
    }

    OrderInfo current_order = new OrderInfo();

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.submit_btn || v.getId() == R.id.confirm_screen_menu_item) {

            Intent i = new Intent("com.example.marc.concessionstand.OrderConfirmation");
            startActivity(i);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.screen_menu, menu);
        return true;
    }}
