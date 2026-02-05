package com.esparta.guru_02.services;

import com.esparta.guru_02.model.BeerCSVRecord;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

/*
 * Author: M
 * Date: 04-Feb-26
 * Project Name: guru-02
 * Description: beExcellent
 */
public class BeerCSVServiceImpl implements BeerCSVService {
    @Override
    public List<BeerCSVRecord> beerCSVRecords(File csvFile)  {

        try {
            List<BeerCSVRecord> beerCSVRecordList = new CsvToBeanBuilder<BeerCSVRecord>(new FileReader(csvFile))
                    .withType(BeerCSVRecord.class)
                    .build().parse();
            return List.copyOf(beerCSVRecordList);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<BeerCSVRecord> beerCSVRecords(String path) {
        // if path null throws illegal argument exception
        if (path == null) {
            throw new IllegalArgumentException("Path must not be null");
        }
        // path must not be empty
        if (path.isEmpty()){
            throw new IllegalArgumentException("Path must not be empty");
        }

        File beerCSVFile = new File(path);

        // Validate that the file exists
        if (!beerCSVFile.exists()) {
            throw new IllegalArgumentException("File does not exist: " + path);
        }

        return beerCSVRecords(beerCSVFile);
    }
}
