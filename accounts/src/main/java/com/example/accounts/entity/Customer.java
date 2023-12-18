package com.example.accounts.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
public class Customer extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long customerId;
    private String name;
    private String email;
    private String mobileNumber;

    @Override
    public int hashCode() {
        return Objects.hash(
                this.customerId,
                this.name,
                this.email,
                this.mobileNumber
        );
    }

    @Override
    public boolean equals(Object obj) {
        Customer customer = (Customer) obj;

        return Objects
                .deepEquals(this.customerId, customer.customerId)
                && Objects.deepEquals(this.email, customer.email)
                && Objects.deepEquals(this.name, customer.name)
                && Objects.deepEquals(this.mobileNumber, customer.mobileNumber);
    }

}
