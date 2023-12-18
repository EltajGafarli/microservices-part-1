package com.example.accounts.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CustomerDto {

    @NotEmpty(message = "Name can not be a null or empty")
    @Size(min = 5, max = 30, message = "The length of the customer name should be between 5 and 30")
    private String name;

    @NotEmpty(message = "Email address can not be a null or empty")
    @Email(message = "Email address should be a valid")
    private String email;

    @Pattern(regexp = "(^$![0-9]{10})", message = "")
    private String mobileNumber;

    private AccountsDto accountsDto;
}
