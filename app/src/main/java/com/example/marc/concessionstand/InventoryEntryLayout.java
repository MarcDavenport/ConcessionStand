package com.example.marc.concessionstand;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.view.View;
import android.widget.TextView;

import java.math.BigDecimal;

/**
 * Created by Marc on 2/28/2017.
 */

public class InventoryEntryLayout extends LinearLayout implements View.OnClickListener {


    public InventoryEntryLayout(Context context) {
        super(context);
        initView(context);
    }

    public InventoryEntryLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public InventoryEntryLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    public InventoryEntryLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }

    private void initView(Context c) {
        LayoutInflater i = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        i.inflate(R.layout.inventory_entry_layout, this);

        ((CheckBox)findViewById(R.id.inventory_for_sale_checkbox)).setOnClickListener(this);
    }

    public void PopulateFields(InventoryEntry entry) {
        TextView id_text = (TextView)findViewById(R.id.inventory_item_id);
        TextView item_text = (TextView)findViewById(R.id.inventory_item_name);
        EditText price_edit_text = (EditText)findViewById(R.id.inventory_item_price_edit_text);
        TextView stock_text = (TextView)findViewById(R.id.inventory_item_quantity);
        CheckBox for_sale_checkbox = (CheckBox)findViewById(R.id.inventory_for_sale_checkbox);

        id_text.setText(Integer.toString(entry.item_id));
        item_text.setText(entry.item_name);
        price_edit_text.setText(entry.item_price.toString());
        stock_text.setText(Integer.toString(entry.item_quantity));
        for_sale_checkbox.setChecked(entry.item_for_sale);
    }



    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.inventory_item_price_edit_text) {
            showSoftKeyboard(v);
        }
    }
    public void showSoftKeyboard(View view) {
        if (view.requestFocus()) {
            InputMethodManager imm = (InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        }
    }
}
