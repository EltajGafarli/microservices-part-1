package com.example.accounts.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.util.Objects;

@Entity

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Accounts extends BaseEntity{

    @Id
    private long accountNumber;

    @Column(name = "account_type")
    private String accountType;

    @Column(name = "customer_id")
    private long customerId;

    @Column(name = "branch_address")
    private String branchAddress;

    @Override
    public int hashCode() {
        return Objects
                .hash(
                        accountNumber,
                        accountType,
                        customerId,
                        branchAddress
                );
    }


    @Override
    public boolean equals(Object obj) {
        Accounts accounts = (Accounts) obj;

        return Objects
                .deepEquals(accounts.accountNumber, this.accountNumber)
                && Objects.deepEquals(accounts.accountType, this.accountType)
                && Objects.deepEquals(accounts.branchAddress, this.branchAddress)
                && Objects.deepEquals(accounts.customerId, this.customerId);
    }
}
