package com.example.loans.serivce;

import com.example.loans.constants.LoansConstants;
import com.example.loans.dto.LoansDto;
import com.example.loans.entity.Loans;
import com.example.loans.exception.LoanAlreadyExistException;
import com.example.loans.exception.ResourceNotFoundException;
import com.example.loans.mapper.LoansMapper;
import com.example.loans.repository.LoansRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class LoansService {
    private final LoansRepository loansRepository;

    @Transactional
    public void createLoan(String mobileNumber) {
        Optional<Loans> optionalLoans = loansRepository.findByMobileNumber(mobileNumber);
        if(optionalLoans.isPresent()) {
            throw new LoanAlreadyExistException(
                    "Loans already exist"
            );
        }
        loansRepository.save(createNewLoan(mobileNumber));
    }

    private Loans createNewLoan(String mobileNumber) {
        Loans newLoan = new Loans();
        long randomLoanNumber =  1000000000L + new Random().nextInt(900000000);
        newLoan.setLoanNumber(Long.toString(randomLoanNumber));
        newLoan.setMobileNumber(mobileNumber);
        newLoan.setLoanType(LoansConstants.HOME_LOAN);
        newLoan.setTotalLoan(LoansConstants.NEW_LOAN_LIMIT);
        newLoan.setAmountPaid(0);
        newLoan.setOutStandingAmount(LoansConstants.NEW_LOAN_LIMIT);
        return newLoan;
    }

    public LoansDto fetchLoan(String mobileNumber) {
        Loans loans = loansRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException(
                        "Loans",
                        "MobileNumber",
                        mobileNumber
                )
        );

        return LoansMapper.mapToLoansDto(loans, new LoansDto());
    }

    @Transactional
    public boolean updateLoan(LoansDto loansDto) {
        Loans loans = loansRepository.findByLoanNumber(loansDto.getLoanNumber()).orElseThrow(
                () -> new ResourceNotFoundException(
                        "Loans",
                        "LoanNumber",
                        loansDto.getLoanNumber()
                )
        );
        LoansMapper.mapToLoans(loansDto, loans);
        loansRepository.save(loans);
        return true;
    }

    @Transactional
    public boolean deleteLoan(String mobileNumber) {
        Loans loans = loansRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException(
                        "Loans",
                        "MobileNumber",
                        mobileNumber
                )
        );
        loansRepository.deleteById(loans.getLoanId());
        return true;
    }

}
