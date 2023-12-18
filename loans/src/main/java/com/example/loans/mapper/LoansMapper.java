package com.example.loans.mapper;

import com.example.loans.dto.LoansDto;
import com.example.loans.entity.Loans;

public class LoansMapper {
    public static LoansDto mapToLoansDto(Loans loans, LoansDto loansDto) {
        loansDto.setLoanType(loans.getLoanNumber());
        loansDto.setLoanNumber(loans.getLoanNumber());
        loansDto.setTotalLoan(loans.getTotalLoan());
        loansDto.setMobileNumber(loans.getMobileNumber());
        loansDto.setAmountPaid(loans.getAmountPaid());
        loansDto.setOutStandingAmount(loans.getOutStandingAmount());
        return loansDto;
    }

    public static Loans mapToLoans(LoansDto loansDto, Loans loans) {
        loans.setOutStandingAmount(loansDto.getOutStandingAmount());
        loans.setLoanNumber(loansDto.getLoanNumber());
        loans.setLoanType(loansDto.getLoanType());
        loans.setMobileNumber(loansDto.getMobileNumber());
        loans.setAmountPaid(loansDto.getAmountPaid());
        return loans;
    }
}
