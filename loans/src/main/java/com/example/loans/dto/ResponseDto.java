package com.example.loans.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class ResponseDto {
    private String statusCode;
    private String statusMsg;
}
