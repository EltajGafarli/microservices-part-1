package com.example.accounts.client;

import com.example.accounts.dto.LoansDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("loans")
@Repository
public interface LoansFeingClient {
    @GetMapping(path = "/api/fetch", consumes = "application/json")
    ResponseEntity<LoansDto> fetchLoansDetails(@RequestParam String mobileNumber);
}
