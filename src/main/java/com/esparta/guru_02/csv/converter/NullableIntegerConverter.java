package com.esparta.guru_02.csv.converter;

import com.opencsv.bean.AbstractBeanField;

/*
 * Author: M
 * Date: 05-Feb-26
 * Project Name: guru-02
 * Description: beExcellent
 */
public class NullableIntegerConverter
        extends AbstractBeanField<Integer, String> {

    @Override
    protected Integer convert(String value) {
        if (value == null || value.isBlank() || value.equalsIgnoreCase("NA")) {
            return null;
        }
        try {
            return Integer.valueOf(value.trim());
        } catch (NumberFormatException ex) {
            return null;
        }
    }
}
