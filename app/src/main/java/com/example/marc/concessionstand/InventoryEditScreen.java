package com.example.marc.concessionstand;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Iterator;

import static android.support.v7.appcompat.R.attr.colorPrimary;

public class InventoryEditScreen extends AppCompatActivity implements View.OnClickListener {

    Inventory inventory;

    LinearLayout selected_layout = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_edit_screen);

        Button b1 = (Button)findViewById(R.id.inventory_remove_item_btn);
        b1.setOnClickListener(this);

        Button b2 = (Button)findViewById(R.id.inventory_add_item_btn);
        b2.setOnClickListener(this);

        Button b3 = (Button)findViewById(R.id.inventory_exit_btn);
        b3.setOnClickListener(this);

        inventory = new Inventory();
        try {
            inventory.GetInventoryFromDatabase();
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

        PopulateInventoryList();
    }

    public void PopulateInventoryList() {

        LinearLayout inventory_scroll_layout = (LinearLayout)findViewById(R.id.inventory_scroll_layout);

        inventory_scroll_layout.removeAllViews();

        ArrayList<InventoryEntry> entries = inventory.GetInventoryEntries();

        Iterator<InventoryEntry> it = entries.iterator();

        while (it.hasNext()) {
            InventoryEntry entry = it.next();
            InventoryEntryLayout entry_layout = new InventoryEntryLayout(this);
            entry_layout.setId(entry.item_id);
            entry_layout.setOnClickListener(this);
            entry_layout.PopulateFields(entry);
            inventory_scroll_layout.addView(entry_layout);
        }
    }


    @Override
    public void onClick(View v) {
        boolean exit = false;
        if (v.getId() == R.id.inventory_remove_item_btn) {
            /// TODO : Remove selected item
        }
        else if (v.getId() == R.id.inventory_add_item_btn) {
            Intent i = new Intent("com.example.marc.concessionstand.AddItemScreen");
            startActivity(i);
        }
        else if (v.getId() == R.id.inventory_exit_btn) {
            exit = true;
        }
        else {
            if (selected_layout != null) {
                selected_layout.setBackgroundColor(getResources().getColor(R.color.colorAccent, getTheme()));
                selected_layout = null;
            }
            selected_layout = (LinearLayout) findViewById(v.getId());
            selected_layout.setBackgroundColor(getResources().getColor(R.color.colorPrimary, getTheme()));
            InputMethodManager imm = (InputMethodManager)v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken() , InputMethodManager.HIDE_NOT_ALWAYS);
        }

        if (exit) {
            /// TODO : Update Inventory???
            finish();
        }
    }
}
