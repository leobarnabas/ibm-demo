package com.example.demo.dao;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Builder
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class BalanceDao {

    @Id
    @GeneratedValue
    Integer balanceId;
    String accountNumber;
    Date lastUpdateTimeStamp;
    double balance;
}
