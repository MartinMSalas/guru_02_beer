package com.esparta.guru_02.services;

import com.esparta.guru_02.model.BeerCSVRecord;

import java.util.List;

/*
 * Author: M
 * Date: 04-Feb-26
 * Project Name: guru-02
 * Description: beExcellent
 */
public interface BeerCSVService {


    List<BeerCSVRecord> beerCSVRecords(String path);
}
