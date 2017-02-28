package com.example.marc.concessionstand;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Marc on 2/27/2017.
 */

public class Inventory {
    public class InventoryEntry {
        protected String item_name;
        protected BigDecimal item_price;
        protected int item_quantity;
        protected boolean item_for_sale;
    }

    List<InventoryEntry> inventory;



}
