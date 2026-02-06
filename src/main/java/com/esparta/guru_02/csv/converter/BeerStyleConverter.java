package com.esparta.guru_02.csv.converter;

import com.esparta.guru_02.model.BeerStyle;
import com.opencsv.bean.AbstractBeanField;

/*
 * Author: M
 * Date: 05-Feb-26
 * Project Name: guru-02
 * Description: beExcellent
 */
public class BeerStyleConverter
        extends AbstractBeanField<BeerStyle, String> {

    @Override
    protected BeerStyle convert(String value) {
        if (value == null || value.isBlank() || value.equalsIgnoreCase("NA")) {
            return BeerStyle.IPA;
        }
        try {
            return BeerStyle.fromCsv(value);

        } catch (IllegalArgumentException ex) {
            return null;
        }
    }
}