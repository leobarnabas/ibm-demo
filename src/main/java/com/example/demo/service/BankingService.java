package com.example.demo.service;

import com.example.demo.dao.BalanceDao;
import com.example.demo.dao.TransactionDao;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

public interface BankingService {
    List<TransactionDao> findTransactions(String accountNumber, String type, Date startTime, Date endTime, Pageable pageable);
    Double getLatestBalance(String accountNumber);
    void saveTransaction(TransactionDao transactionDao);
    void saveBalance(BalanceDao balanceDao);
    void sendMessage(String topic, String payload);
}
