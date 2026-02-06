package com.esparta.guru_02.csv.converter;

import com.opencsv.bean.AbstractBeanField;

import java.math.BigDecimal;

/*
 * Author: M
 * Date: 05-Feb-26
 * Project Name: guru-02
 * Description: beExcellent
 */
public class NullableBigDecimalConverter
        extends AbstractBeanField<BigDecimal, String> {

    @Override
    protected BigDecimal convert(String value) {
        if (value == null || value.isBlank() || value.equalsIgnoreCase("NA")) {
            return null;
        }
        try {
            return new BigDecimal(value.trim());
        } catch (NumberFormatException ex) {
            return null;
        }
    }
}