package com.example.marc.concessionstand;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class OrderConfirmation extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirmation);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.app_bar_confirm);
        setSupportActionBar(myToolbar);
    }

    public void onClick(View v) {
        if (v.getId() == R.id.order_screen_menu_item) {
            Intent i = new Intent("com.example.marc.concessionstand.MainActivity");
            startActivity(i);
        }
    }

}
