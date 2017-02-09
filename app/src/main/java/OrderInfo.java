import java.math.BigDecimal;

/**
 * Created by Marc on 2/9/2017.
 */

public class OrderInfo {

    protected class Order {

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
