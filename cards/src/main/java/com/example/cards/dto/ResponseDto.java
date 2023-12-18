package com.example.cards.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ResponseDto {

    private String statusCode;
    private String statusMsg;
}
