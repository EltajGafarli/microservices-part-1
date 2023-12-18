package com.example.loans.controller;

import com.example.loans.constants.LoansConstants;
import com.example.loans.dto.LoansDto;
import com.example.loans.dto.ResponseDto;
import com.example.loans.serivce.LoansService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@RequiredArgsConstructor
@Validated
public class LoansController {
    private final LoansService loansService;

    @PostMapping(path = "/create")
    public ResponseEntity<ResponseDto> createLoan(@RequestParam String mobileNumber) {
        loansService.createLoan(mobileNumber);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        ResponseDto.builder()
                                .statusCode(LoansConstants.STATUS_201)
                                .statusMsg(LoansConstants.MESSAGE_201)
                                .build()
                );
    }

    @GetMapping(path = "/fetch")
    public ResponseEntity<LoansDto> fetchLoanDetails(@RequestParam String mobileNumber) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(loansService.fetchLoan(mobileNumber));
    }

    @PutMapping(path = "/update")
    public ResponseEntity<ResponseDto> updateLoanDetails(@Valid @RequestBody LoansDto loansDto) {
        loansService.updateLoan(loansDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto(LoansConstants.STATUS_200, LoansConstants.MESSAGE_200));
    }


    @DeleteMapping(path = "/delete")
    public ResponseEntity<ResponseDto> deleteLoanDetails(@RequestParam String mobileNumber) {
        loansService.deleteLoan(mobileNumber);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto(LoansConstants.STATUS_200, LoansConstants.MESSAGE_200));
    }

}
