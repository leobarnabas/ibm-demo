package com.example.demo.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDao {
    @Id
    @GeneratedValue
    private Integer id;
    private String accountNumber;
    private String type;
    private Date transactionTs;
    private double amount;
}
