package com.example.marc.concessionstand;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marc on 2/27/2017.
 */

public class Inventory {

    protected ArrayList<InventoryEntry> inventory = null;

    public Inventory() {}

    public void GetInventoryFromDatabase() throws Exception {
        try {
            MySQLDatabase db = new MySQLDatabase();
            inventory = db.GetInventoryFromDatabase();
        }
        catch (Exception e) {
            throw new Exception(e);
        }
    }

    public ArrayList<InventoryEntry> GetInventoryEntries() {
        return inventory;
    }

}
