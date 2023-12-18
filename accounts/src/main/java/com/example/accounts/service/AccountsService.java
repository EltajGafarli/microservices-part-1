package com.example.accounts.service;

import com.example.accounts.constants.AccountConstants;
import com.example.accounts.dto.AccountsDto;
import com.example.accounts.dto.CustomerDto;
import com.example.accounts.entity.Accounts;
import com.example.accounts.entity.Customer;
import com.example.accounts.exceptions.CustomerAlreadyExistsException;
import com.example.accounts.exceptions.ResourcesNotFoundException;
import com.example.accounts.mappper.AccountsMapper;
import com.example.accounts.mappper.CustomerMapper;
import com.example.accounts.repository.AccountsRepository;
import com.example.accounts.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AccountsService {
    private final AccountsRepository accountsRepository;
    private final CustomerRepository customerRepository;

    @Transactional
    public void createAccount(CustomerDto customerDto){
        Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());
        Optional<Customer> optionalCustomer = customerRepository.findCustomerByMobileNumber(customerDto.getMobileNumber());
        if(optionalCustomer.isPresent()) {
            throw new CustomerAlreadyExistsException("Customer already registered with given mobile Number " + customerDto.getMobileNumber());
        }
        Customer savedCustomer = customerRepository.save(customer);
        accountsRepository.save(createNewAccount(savedCustomer));
    }

    public Accounts createNewAccount(Customer customer) {
        Accounts accounts = new Accounts();
        accounts.setCustomerId(customer.getCustomerId());
        long randomAccNumber = 1000000000L + new Random().nextInt(900000000);

        accounts.setAccountNumber(randomAccNumber);
        accounts.setAccountType(AccountConstants.SAVINGS);
        accounts.setBranchAddress(AccountConstants.ADDRESS);
        return accounts;
    }

    public CustomerDto fetchAccount(String mobileNumber) {
        Customer customer = customerRepository.findCustomerByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourcesNotFoundException("Customer", "mobileNumber", mobileNumber)
        );

        AccountsDto accountsDto = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourcesNotFoundException(
                        "Accounts",
                        "customerId",
                        Long.toString(customer.getCustomerId()))
        );

        CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer, new CustomerDto());
        customerDto.setAccountsDto(
                accountsDto
        );

        return customerDto;

    }

    @Transactional
    public boolean updateAccount(CustomerDto customerDto) {
        boolean isUpdated = true;
        AccountsDto accountsDto = customerDto.getAccountsDto();
        if(accountsDto != null) {
            Accounts accounts = accountsRepository.findById(accountsDto.getAccountNumber())
                    .orElseThrow(() -> new ResourcesNotFoundException("Accounts", "AccountNumber", Long.toString(accountsDto.getAccountNumber())));

            AccountsMapper.mapToAccounts(accountsDto, accounts);
            accounts = accountsRepository.save(accounts);
            long customerId = accounts.getCustomerId();
            Customer customer = customerRepository.findById(customerId).orElseThrow(
                    () -> new ResourcesNotFoundException("Customer", "CustomerId", Long.toString(customerId))
            );

            CustomerMapper.mapToCustomer(customerDto, customer);
            customerRepository.save(customer);
            isUpdated = false;
        }
        return isUpdated;
    }


    public boolean deleteAccount(String mobileNumber) {
        Customer customer = customerRepository.findCustomerByMobileNumber(mobileNumber)
                .orElseThrow(() -> new ResourcesNotFoundException("Customer", "Mobile Number", mobileNumber));

        accountsRepository.deleteByCustomerId(customer.getCustomerId());
        customerRepository.deleteById(customer.getCustomerId());
        return true;
    }
}
