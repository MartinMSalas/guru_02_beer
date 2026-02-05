package com.esparta.guru_02.services;

import com.esparta.guru_02.model.BeerCSVRecord;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BeerCSVServiceImplTest {

    private final BeerCSVServiceImpl service = new BeerCSVServiceImpl();

    @Test
    void givenNullPath_whenBeerCSVRecordsCalled_thenThrowIllegalArgumentException() {
        // given
        String path = null;

        // when & then
        assertThrows(
                IllegalArgumentException.class,
                () -> service.beerCSVRecords(path)
        );
    }

    @Test
    void givenEmptyPath_whenBeerCSVRecordsCalled_thenThrowIllegalArgumentException() {
        // given
        String path = "";

        // when & then
        assertThrows(
                IllegalArgumentException.class,
                () -> service.beerCSVRecords(path)
        );
    }

    @Test
    void givenNonExistingPath_whenBeerCSVRecordsCalled_thenThrowIllegalArgumentException() {
        // given
        String path = "does-not-exist.csv";

        // when & then
        assertThrows(
                IllegalArgumentException.class,
                () -> service.beerCSVRecords(path)
        );
    }

    @Test
    void givenValidPath_whenBeerCSVRecordsCalled_thenReturnNonNullList() {
        // given
        String path = "src/test/resources/empty-beer.csv";

        // when
        List<BeerCSVRecord> records = service.beerCSVRecords(path);

        // then
        assertNotNull(records);
    }

    @Test
    void givenValidPathWithEmptyFile_whenBeerCSVRecordsCalled_thenReturnEmptyList() {
        // given
        String path = "src/test/resources/empty-beer.csv";

        // when
        List<BeerCSVRecord> records = service.beerCSVRecords(path);

        // then
        assertTrue(records.isEmpty());
    }

    @Test
    void givenReturnedList_whenModified_thenThrowUnsupportedOperationException() {
        // given
        String path = "src/test/resources/empty-beer.csv";
        List<BeerCSVRecord> records = service.beerCSVRecords(path);

        // when & then
        assertThrows(
                UnsupportedOperationException.class,
                () -> records.add(null)
        );
    }

    @Test
    void givenValidPath_whenCalledMultipleTimes_thenResultsAreConsistent() {
        // given
        String path = "src/test/resources/empty-beer.csv";

        // when
        List<BeerCSVRecord> first = service.beerCSVRecords(path);
        List<BeerCSVRecord> second = service.beerCSVRecords(path);

        // then
        assertEquals(first, second);
    }

    @Test
    void givenValidPath_whenCalledMultipleTimes_thenReturnDifferentInstances() {
        // given
        String path = "src/test/resources/empty-beer.csv";

        // when
        List<BeerCSVRecord> first = service.beerCSVRecords(path);
        List<BeerCSVRecord> second = service.beerCSVRecords(path);

        // then
        assertNotSame(first, second);
    }

    @Test
    void givenValidPath_whenRecordsReturned_thenAllElementsAreBeerCSVRecord() {
        // given
        String path = "src/test/resources/empty-beer.csv";

        // when
        List<BeerCSVRecord> records = service.beerCSVRecords(path);

        // then
        records.forEach(record ->
                assertInstanceOf(BeerCSVRecord.class, record)
        );
    }
}


