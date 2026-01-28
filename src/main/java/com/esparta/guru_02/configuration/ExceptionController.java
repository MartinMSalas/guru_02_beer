package com.esparta.guru_02.configuration;

import com.esparta.guru_02.controllers.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/*
 * Author: m
 * Date: 28/1/26
 * Project Name: guru_02_beer
 * Description: beExcellent
 */
@Slf4j
//@ControllerAdvice
public class ExceptionController {
  //  @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ProblemDetail> handleNotFoundException(NotFoundException ex) {

        log.debug("In BeerController.hanldeNotFoundException() with message: {}",ex.getMessage());

        ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        pd.setTitle("Not Found");
        pd.setDetail(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(pd);
    }
}
