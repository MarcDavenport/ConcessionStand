package com.example.marc.concessionstand;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WorkerMenu extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_menu);

        Button b1 = (Button)findViewById(R.id.main_edit_inventory_btn);
        Button b2 = (Button)findViewById(R.id.main_view_sales_btn);
        Button b3 = (Button)findViewById(R.id.main_view_logout_btn);

        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        b3.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.main_edit_inventory_btn) {
            Intent i = new Intent("com.example.marc.concessionstand.InventoryEditScreen");
            startActivity(i);
        }
        if (v.getId() == R.id.main_view_sales_btn) {
            Intent i = new Intent("com.example.marc.concessionstand.ViewSalesScreen");
            startActivity(i);
        }
        if (v.getId() == R.id.main_view_logout_btn) {
            finish();
        }
    }
}
