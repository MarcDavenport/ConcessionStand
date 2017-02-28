package com.example.marc.concessionstand;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Created by Marc on 2/27/2017.
 */

public class Sale {

    public class ItemSale {
        String item_name;
        int item_quantity_sold;
        BigDecimal item_price;
    }

    protected ArrayList<ItemSale> sale = new ArrayList<ItemSale>();

}
