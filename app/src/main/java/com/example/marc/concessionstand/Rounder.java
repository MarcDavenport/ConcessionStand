package com.example.marc.concessionstand;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * Created by Marc on 2/28/2017.
 */

public class Rounder {
    public static BigDecimal RoundBigDecimalToNearestQuarter(BigDecimal old_price) {
        BigDecimal new_price = new BigDecimal(old_price.toString());
        new_price.multiply(new BigDecimal("4.00"));
        new_price.add(new BigDecimal("0.5"));
        new_price.setScale(0 , RoundingMode.DOWN);
        return new_price;
    }
}
