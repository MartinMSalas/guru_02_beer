package com.esparta.guru_02.services;

import com.esparta.guru_02.model.BeerCSVRecord;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;

import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for BeerCSVServiceImpl.
 *
 * Scope:
 * - Validate contract behavior only
 * - Do NOT impose implementation choices
 * - Detect null returns explicitly
 * - Detect immutability violations
 */
/**
 * Unit tests for BeerCSVServiceImpl.
 *
 * Rules enforced by this test suite:
 * - Execution order is deterministic
 * - Returned values MUST NOT be null
 * - Invalid inputs MUST fail fast
 * - Returned collections MUST be immutable
 * - Tests do NOT assume implementation details
 */
@TestMethodOrder(OrderAnnotation.class)
@Execution(ExecutionMode.SAME_THREAD)
class BeerCSVServiceImplTest {

    private static final String VALID_EMPTY_TEST_PATH =
            "src/test/resources/empty-beer.csv";
    private static final String VALID_BEER_CSV_TEST_PATH = "src/test/resources/valid-beer.csv";

    private final BeerCSVServiceImpl service = new BeerCSVServiceImpl();

    /* =========================================================
       INPUT VALIDATION
       ========================================================= */

    @Test
    @Order(1)
    void givenNullPath_whenBeerCSVRecordsCalled_thenThrowIllegalArgumentException() {
        // given
        String path = null;

        // when / then
        assertThrows(
                IllegalArgumentException.class,
                () -> service.beerCSVRecords(path),
                "Null path must be rejected explicitly"
        );
    }

    @Test
    @Order(2)
    void givenEmptyPath_whenBeerCSVRecordsCalled_thenThrowIllegalArgumentException() {
        // given
        String path = "";

        // when / then
        assertThrows(
                IllegalArgumentException.class,
                () -> service.beerCSVRecords(path),
                "Empty path must be rejected explicitly"
        );
    }

    @Test
    @Order(3)
    void givenNonExistingPath_whenBeerCSVRecordsCalled_thenThrowIllegalArgumentException() {
        // given
        String path = "does-not-exist.csv";

        // when / then
        assertThrows(
                IllegalArgumentException.class,
                () -> service.beerCSVRecords(path),
                "Non-existing path must be rejected explicitly"
        );
    }

    /* =========================================================
       VALID PATH – CONTRACT ENFORCEMENT
       ========================================================= */
    @Test
    @Order(4)
    void givenValidPath_whenBeerCSVRecordsCalled_thenReturnNonNullList() {
        // given
        String path = VALID_BEER_CSV_TEST_PATH;

        // when
        List<BeerCSVRecord> records = service.beerCSVRecords(path);

        // then
        assertNotNull(
                records,
                "Service must never return null for a valid path"
        );
        assertThat(records.size()).isGreaterThan(0);
    }

    @Test
    @Order(5)
    void givenValidPathWithEmptyFile_whenBeerCSVRecordsCalled_thenReturnEmptyList() {
        // given
        String path = VALID_EMPTY_TEST_PATH;

        // when
        List<BeerCSVRecord> records = service.beerCSVRecords(path);

        // then
        assertNotNull(records, "Returned list must not be null");
        assertTrue(
                records.isEmpty(),
                "Empty CSV file must result in an empty list"
        );
    }

    /* =========================================================
       IMMUTABILITY GUARANTEES
       ========================================================= */

    @Test
    @Order(6)
    void givenReturnedList_whenAddAttempted_thenThrowUnsupportedOperationException() {
        // given
        List<BeerCSVRecord> records = service.beerCSVRecords(VALID_BEER_CSV_TEST_PATH);

        // sanity
        assertNotNull(records, "Returned list must not be null");
        assertThat(records.size()).isGreaterThan(0);
        // when / then
        assertThrows(
                UnsupportedOperationException.class,
                () -> records.add(null),
                "Returned list must be immutable"
        );
    }

    @Test
    @Order(7)
    void givenReturnedList_whenClearAttempted_thenThrowUnsupportedOperationException() {
        // given
        List<BeerCSVRecord> records = service.beerCSVRecords(VALID_BEER_CSV_TEST_PATH);
        assertThat(records.size()).isGreaterThan(0);
        // sanity
        assertNotNull(records, "Returned list must not be null");
        assertThat(records.size()).isGreaterThan(0);

        // when / then
        assertThrows(
                UnsupportedOperationException.class,
                records::clear,
                "Returned list must be immutable"
        );
    }

    /* =========================================================
       CONSISTENCY & TYPE SAFETY
       ========================================================= */

    @Test
    @Order(8)
    void givenValidPath_whenCalledMultipleTimes_thenResultsAreEqualButNotSameInstance() {
        // given
        String path = VALID_BEER_CSV_TEST_PATH;

        // when
        List<BeerCSVRecord> first = service.beerCSVRecords(path);
        List<BeerCSVRecord> second = service.beerCSVRecords(path);

        // then
        assertNotNull(first, "First call must not return null");
        assertNotNull(second, "Second call must not return null");

        assertEquals(
                first,
                second,
                "Results must be consistent across calls"
        );

        assertNotSame(
                first,
                second,
                "Each call must return a new list instance"
        );
    }

    @Test
    @Order(9)
    void givenValidPath_whenRecordsReturned_thenAllElementsAreBeerCSVRecord() {
        // given
        String path = VALID_BEER_CSV_TEST_PATH;

        // when
        List<BeerCSVRecord> records = service.beerCSVRecords(path);
        assertThat(records.size()).isGreaterThan(0);
        // then
        assertNotNull(records, "Returned list must not be null");

        records.forEach(record ->
                assertInstanceOf(
                        BeerCSVRecord.class,
                        record,
                        "All elements must be of type BeerCSVRecord"
                )
        );
    }
}