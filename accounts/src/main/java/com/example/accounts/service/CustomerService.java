package com.example.accounts.service;

import com.example.accounts.client.CardsFeingClient;
import com.example.accounts.client.LoansFeingClient;
import com.example.accounts.dto.CardsDto;
import com.example.accounts.dto.CustomerDetailsDto;
import com.example.accounts.dto.CustomerDto;
import com.example.accounts.dto.LoansDto;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final AccountsService accountsService;
    private final CardsFeingClient cardsFeingClient;
    private final LoansFeingClient loansFeingClient;

    public CustomerDetailsDto CustomerDetailsDtoFetchCustomerDetails(String mobileNumber) {
        CustomerDto customerDto = this.accountsService.fetchAccount(mobileNumber);
        LoansDto loansDto = loansFeingClient.fetchLoansDetails(mobileNumber).getBody();
        CardsDto cardsDto = cardsFeingClient.fetchCardDetails(mobileNumber).getBody();

        return CustomerDetailsDto
                .builder()
                .name(customerDto.getName())
                .email(customerDto.getEmail())
                .accountsDto(customerDto.getAccountsDto())
                .mobileNumber(customerDto.getMobileNumber())
                .loansDto(loansDto)
                .cardsDto(cardsDto)
                .build();
    }

}
