package com.example.accounts.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ResponseDto {
    private String statusCode;
    private String statusMessage;
}
