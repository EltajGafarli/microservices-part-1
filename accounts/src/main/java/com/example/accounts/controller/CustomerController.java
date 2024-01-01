package com.example.accounts.controller;

import com.example.accounts.client.CardsFeingClient;
import com.example.accounts.client.LoansFeingClient;
import com.example.accounts.dto.CardsDto;
import com.example.accounts.dto.CustomerDetailsDto;
import com.example.accounts.dto.CustomerDto;
import com.example.accounts.dto.LoansDto;
import com.example.accounts.service.AccountsService;
import com.example.accounts.service.CustomerService;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@RequiredArgsConstructor
@Validated
public class CustomerController {

    private final CustomerService customerService;
    @GetMapping(path = "/fetchCustomerDetails")
    public ResponseEntity<CustomerDetailsDto> fetchCustomerDetails(@RequestParam @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits") String mobileNumber) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        customerService
                                .CustomerDetailsDtoFetchCustomerDetails(
                                        mobileNumber
                                )
                );
    }
}
