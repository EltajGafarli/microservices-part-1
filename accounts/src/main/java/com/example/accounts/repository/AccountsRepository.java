package com.example.accounts.repository;

import com.example.accounts.dto.AccountsDto;
import com.example.accounts.entity.Accounts;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountsRepository extends JpaRepository<Accounts, Long> {
    Optional<AccountsDto> findByCustomerId(long id);

    @Transactional
    @Modifying
    void deleteByCustomerId(long customerId);
}
