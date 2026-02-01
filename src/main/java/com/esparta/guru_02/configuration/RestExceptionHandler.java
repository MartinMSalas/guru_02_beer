package com.esparta.guru_02.configuration;

import com.esparta.guru_02.exceptions.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/*
 * Author: m
 * Date: 28/1/26
 * Project Name: guru_02_beer
 * Description: beExcellent
 */
/**
 * Centralized REST exception handling.
 *
 * Translates domain and infrastructure exceptions
 * into stable HTTP responses without leaking internals.
 */
@Slf4j
@RestControllerAdvice
public class RestExceptionHandler {

    /* =========================================================
       VALIDATION (400)
       ========================================================= */

    /**
     * Handles validation errors for request bodies annotated with @Valid / @Validated.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProblemDetail> handleValidation(
            MethodArgumentNotValidException ex) {

        log.debug("Validation failed");

        ProblemDetail problemDetail =
                ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);

        problemDetail.setTitle("Validation Failed");
        problemDetail.setDetail("One or more fields have invalid values");
        problemDetail.setType(URI.create("https://api.example.com/problems/validation-error"));

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error ->
                        errors.put(error.getField(), error.getDefaultMessage())
                );

        problemDetail.setProperty("errors", errors);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(problemDetail);
    }

    /* =========================================================
       DOMAIN ERRORS
       ========================================================= */

    /**
     * Maps domain NotFoundException → HTTP 404.
     */
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ProblemDetail> handleNotFound(NotFoundException ex) {

        log.debug("Handling NotFoundException: {}", ex.getMessage());

        ProblemDetail problemDetail =
                ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problemDetail.setTitle("Resource Not Found");
        problemDetail.setDetail(ex.getMessage());
        problemDetail.setType(URI.create("https://api.example.com/problems/not-found"));

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(problemDetail);
    }

    /* =========================================================
       CONCURRENCY
       ========================================================= */

    /**
     * Maps optimistic locking conflicts → HTTP 409.
     */
    @ExceptionHandler(ObjectOptimisticLockingFailureException.class)
    public ResponseEntity<ProblemDetail> handleOptimisticLocking(
            ObjectOptimisticLockingFailureException ex) {

        log.debug("Optimistic locking conflict");

        ProblemDetail problemDetail =
                ProblemDetail.forStatus(HttpStatus.CONFLICT);
        problemDetail.setTitle("Concurrent Modification");
        problemDetail.setDetail(
                "The resource was modified by another transaction. Please retry."
        );
        problemDetail.setType(URI.create("https://api.example.com/problems/concurrent-modification"));

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(problemDetail);
    }

    /* =========================================================
       CLIENT ERRORS
       ========================================================= */

    /**
     * Handles explicit bad client requests.
     *
     * IMPORTANT: Only throw IllegalArgumentException intentionally
     * for invalid client input.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ProblemDetail> handleBadRequest(IllegalArgumentException ex) {

        log.debug("Bad request: {}", ex.getMessage());

        ProblemDetail problemDetail =
                ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle("Bad Request");
        problemDetail.setDetail(ex.getMessage());
        problemDetail.setType(URI.create("https://api.example.com/problems/bad-request"));

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(problemDetail);
    }

    /* =========================================================
       PERSISTENCE
       ========================================================= */

    /**
     * Handles database constraint violations.
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ProblemDetail> handleDataIntegrityViolation(
            DataIntegrityViolationException ex) {

        log.debug("Data integrity violation");

        ProblemDetail problemDetail =
                ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle("Data Integrity Violation");
        problemDetail.setDetail(
                "The request violates one or more data constraints."
        );
        problemDetail.setType(URI.create("https://api.example.com/problems/data-integrity"));

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(problemDetail);
    }

    /* =========================================================
       FALLBACK (500)
       ========================================================= */

    /**
     * Fallback handler for unexpected errors.
     * Prevents stack traces from leaking to clients.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemDetail> handleGeneric(Exception ex) {

        log.error("Unhandled exception", ex);

        ProblemDetail problemDetail =
                ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        problemDetail.setTitle("Internal Server Error");
        problemDetail.setDetail("An unexpected error occurred");
        problemDetail.setType(URI.create("https://api.example.com/problems/internal-error"));

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(problemDetail);
    }
}