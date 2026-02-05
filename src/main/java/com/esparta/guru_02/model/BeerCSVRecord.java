package com.esparta.guru_02.model;

import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * Author: M
 * Date: 04-Feb-26
 * Project Name: guru-02
 * Description: beExcellent
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BeerCSVRecord {

//    "","count.x","abv","ibu","id","beer","style","brewery_id","ounces","style2","count.y","brewery","city","state","label"

    @CsvBindByName
    private Integer row;
    @CsvBindByName(column = "count.x")
    private Integer count;
    @CsvBindByName
    private Double abv;
    @CsvBindByName
    private Double ibu;
    @CsvBindByName(column = "id")
    private Integer beerId;
    @CsvBindByName
    private String beer;
    @CsvBindByName
    private String style;
    @CsvBindByName(column = "brewery_id")
    private Integer breweryId;
    @CsvBindByName
    private Double ounces;
    @CsvBindByName
    private String style2;
    @CsvBindByName(column = "count.y")
    private Integer count_y;
    @CsvBindByName
    private String brewery;
    @CsvBindByName
    private String city;
    @CsvBindByName
    private String state;
    @CsvBindByName
    private String label;
}
