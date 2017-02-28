package com.example.marc.concessionstand;

import java.math.BigDecimal;

/**
 * Created by Marc on 2/27/2017.
 */

public class InventoryEntry {
    public int item_id;
    public String item_name;
    public BigDecimal item_price;
    public int item_quantity;
    public boolean item_for_sale;

    public InventoryEntry() {
        item_id = -1;
        item_name = "Invalid Item Name";
        item_price = new BigDecimal("0.00");
        item_quantity = 0;
        item_for_sale = false;
    }

    public void SetFields(int id , String name , BigDecimal price , int quantity , boolean for_sale) {
        item_id = id;
        item_name = name;
        item_price = price;
        item_quantity = quantity;
        item_for_sale = for_sale;
    }

    public String toString() {
        return "[ItemID = " + Integer.toString(item_id) + " , ItemName = " + item_name + " , ItemPrice = " + item_price.toString() +
                " , Quantity = " + Integer.toString(item_quantity) + " , ItemForSale = " + (item_for_sale?"true":"false") + "]";
    }

}
