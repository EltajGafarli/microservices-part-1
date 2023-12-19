package com.example.accounts.controller;

import com.example.accounts.dto.AccountsContactInfoDto;
import com.example.accounts.dto.CustomerDto;
import com.example.accounts.dto.ResponseDto;
import com.example.accounts.service.AccountsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import com.example.accounts.constants.AccountConstants;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@RequiredArgsConstructor
@Validated
public class AccountsController {

    private final AccountsService accountsService;
    private final Environment environment;
    private final AccountsContactInfoDto accountsContactInfoDto;

    @Value("${build.version}")
    private String buildVersion;

    @GetMapping(path = "/contact-info")
    public ResponseEntity<AccountsContactInfoDto> contactInfo() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(accountsContactInfoDto);
    }

    @GetMapping(path = "/java-home")
    public ResponseEntity<?> getJavaVersion() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(Map.of(
                        "Java Home", Objects.requireNonNull(environment.getProperty("build.version"))
                ));
    }

    @GetMapping(path = "/build-version")
    public ResponseEntity<?> getBuildVersion() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(Map.of("buildVersion", buildVersion));
    }

    @GetMapping(path = "/fetch")
    public CustomerDto fetchAccountDetails(@RequestParam String mobileNumber) {
        return accountsService.fetchAccount(mobileNumber);
    }


    @Operation(
            summary = "Create Account Rest Api",
            description = "REST API to create new Customer & Account inside EazyBank"
    )
    @ApiResponse(
            responseCode = "201",
            description = "HTTP Status Created"
    )
    @PostMapping(path = "/create")
    public ResponseEntity<ResponseDto> createAccount(@Valid @RequestBody CustomerDto customerDto) {
        accountsService.createAccount(customerDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        ResponseDto
                                .builder()
                                .statusCode(AccountConstants.STATUS_201)
                                .statusMessage(AccountConstants.MESSAGE_201)
                                .build()
                );
    }


    @PutMapping(path = "/update")
    public ResponseEntity<ResponseDto> updateAccount(@Valid @RequestBody CustomerDto customerDto) {
        boolean isUpdated = accountsService.updateAccount(customerDto);
        if(!isUpdated) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(
                            ResponseDto
                                    .builder()
                                    .statusCode(AccountConstants.STATUS_500)
                                    .statusMessage(AccountConstants.MESSAGE_500)
                                    .build()
                    );
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        ResponseDto
                                .builder()
                                .statusCode(AccountConstants.STATUS_200)
                                .statusMessage(AccountConstants.MESSAGE_200)
                                .build()
                );
    }


    @DeleteMapping(path = "/delete")
    public ResponseEntity<ResponseDto> deleteAccountDetails(@Valid @RequestParam String mobileNumber) {
        accountsService.deleteAccount(mobileNumber);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        ResponseDto.builder()
                                .statusCode(AccountConstants.STATUS_200)
                                .statusMessage(AccountConstants.MESSAGE_200)
                                .build()
                );
    }
}
