package com.example.marc.concessionstand;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;

public class AddItemScreen extends AppCompatActivity
            implements View.OnClickListener , TextView.OnEditorActionListener , CompoundButton.OnCheckedChangeListener {

    /// TODO : Implement field population for current_entry upon selection

    InventoryEntry current_entry = new InventoryEntry();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item_screen);

        Button b1 = (Button)findViewById(R.id.add_item_add_item_btn);
        Button b2 = (Button)findViewById(R.id.add_item_update_item_btn);
        Button b3 = (Button)findViewById(R.id.add_item_remove_item_btn);
        Button b4 = (Button)findViewById(R.id.add_item_back_btn);

        EditText et1 = (EditText)findViewById(R.id.add_item_name_edit_text);
        EditText et2 = (EditText)findViewById(R.id.add_item_price_edit_text);
        EditText et3 = (EditText)findViewById(R.id.add_item_quantity_edit_text);

        CheckBox cb1 = (CheckBox)findViewById(R.id.add_item_for_sale_checkbox);

        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        b3.setOnClickListener(this);
        b4.setOnClickListener(this);

        et1.setOnEditorActionListener(this);
        et2.setOnEditorActionListener(this);
        et3.setOnEditorActionListener(this);

        cb1.setOnCheckedChangeListener(this);

        PopulateDropDownList();
    }

    protected void PopulateDropDownList() {

        Inventory inventory = new Inventory();
        try {
            inventory.GetInventoryFromDatabase();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

        ArrayList<InventoryEntry> entries = inventory.GetInventoryEntries();
        ArrayList<String> entry_names = new ArrayList<String>();

        Iterator<InventoryEntry> it = entries.iterator();
        while (it.hasNext()) {
            InventoryEntry entry = it.next();
            entry_names.add(entry.item_name);
        }


        Spinner spinner = (Spinner) findViewById(R.id.inventory_addition_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(getApplicationContext() , R.layout.drop_down_list_layout , R.id.inventory_ddl_text_view , entry_names);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(R.layout.drop_down_list_layout);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
    }


    @Override
    public void onClick(View v) {

        EditText name_text = (EditText)findViewById(R.id.add_item_name_edit_text);
        EditText price_text = (EditText)findViewById(R.id.add_item_price_edit_text);
        EditText quantity_text = (EditText)findViewById(R.id.add_item_quantity_edit_text);

        InventoryEntry entry = new InventoryEntry();

        BigDecimal price = null;
        try {
            price = new BigDecimal(price_text.getText().toString());
            if (price.compareTo(new BigDecimal("0.00")) < 0) {
                price = new BigDecimal("0.00");
            }
        }
        catch (NumberFormatException e) {
            price = new BigDecimal("0.00");
        }

        int quantity = 0;
        try {
            quantity = Integer.parseInt(quantity_text.getText().toString());
            if (quantity < 0) {quantity = 0;}
        }
        catch (NumberFormatException e) {
            quantity = 0;
        }


        CheckBox cb1 = (CheckBox)findViewById(R.id.add_item_for_sale_checkbox);



        /// id , name , price , quantity , for_sale
        entry.SetFields(-1 , name_text.getText().toString() , price , quantity , cb1.isChecked());


        try {
            MySQLDatabase db = null;
            if (v.getId() == R.id.add_item_add_item_btn) {
                db = new MySQLDatabase();
                db.AddItemToInventory(entry);
            }
            if (v.getId() == R.id.add_item_update_item_btn) {
                db = new MySQLDatabase();
                db.UpdateItemInInventory(entry);
            }
            if (v.getId() == R.id.add_item_remove_item_btn) {
            /* TODO : Prompt user to acknowledge item removal */
                db = new MySQLDatabase();
                db.RemoveItemFromInventory(entry.item_name);
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        if (v.getId() == R.id.add_item_back_btn) {
            finish();
        }
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        EditText name_text = (EditText)findViewById(R.id.add_item_name_edit_text);
        EditText quantity_text = (EditText)findViewById(R.id.add_item_quantity_edit_text);
        EditText price_text = (EditText)findViewById(R.id.add_item_price_edit_text);

        if (event != null) {
            if (v.getId() == R.id.add_item_name_edit_text) {
                current_entry.item_name = name_text.getText().toString();
            }
            if (v.getId() == R.id.add_item_quantity_edit_text) {
                current_entry.item_quantity = Integer.parseInt(quantity_text.getText().toString());
            }
            if (v.getId() == R.id.add_item_price_edit_text) {
                current_entry.item_price = new BigDecimal(price_text.getText().toString());
                current_entry.item_price = Rounder.RoundBigDecimalToNearestQuarter(current_entry.item_price);
                price_text.setText(current_entry.item_price.toString());
            }
        }
        return true;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (buttonView.getId() == R.id.add_item_for_sale_checkbox) {
            current_entry.item_for_sale = isChecked;
        }
    }
}
