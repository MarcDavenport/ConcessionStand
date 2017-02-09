package com.example.marc.concessionstand;
/**
 * Created by Marc on 2/9/2017.
 */

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;


public class OrderInfo {

    protected HashMap<String , ArrayList<Order>> current_orders = new HashMap<String , ArrayList<Order>>();

    protected class Order {

        protected HashMap<String , ItemOrder> order_map = new HashMap<String , ItemOrder>();

        public class ItemOrder {

            public class ItemInfo {
                public BigDecimal price = new BigDecimal("0.00");
                public String name;
            }

            public ItemInfo item = new ItemInfo();
            public int quantity = 0;

            public void SetPrice(String price_string) {
                item.price = new BigDecimal(price_string);
            }
            public void SetName(String item_name) {
                item.name = item_name;
            }
            public void SetQuantity(int q) {
                if (q < 0) {q = 0;}
                quantity = q;
            }

            public BigDecimal GetItemOrderPrice() {
                BigDecimal result = new BigDecimal(quantity).multiply(item.price);
                return result;
            }
            public int GetItemQuantity() {return quantity;}
            public String GetItemName() {return item.name;}
            public BigDecimal GetItemPrice() {return item.price;}

        }

        /// protected ItemOrder item_order = new ItemOrder();


    }

}
