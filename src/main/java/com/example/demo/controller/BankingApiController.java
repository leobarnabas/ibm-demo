package com.example.demo.controller;

import com.example.demo.dao.BalanceDao;
import com.example.demo.dao.TransactionDao;
import com.example.demo.service.BankingService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Date;
import java.util.List;

import static com.example.demo.constants.AppConstants.TOPIC_BALANCE;
import static com.example.demo.constants.AppConstants.TOPIC_TRANSACTION;

@RestController
@RequestMapping("/api")
@Validated
@Slf4j
public class BankingApiController {
    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    BankingService bankingService;

    @PostMapping("/sendTransaction")
    public void sendTransactionMessage(TransactionDao transactionDao) throws JsonProcessingException {
        bankingService.sendMessage(TOPIC_TRANSACTION, objectMapper.writeValueAsString(transactionDao));
    }

    @PostMapping("/sendBalance")
    public void sendBalanceMessage(BalanceDao balanceDao) throws JsonProcessingException {
        log.info("POST request:" + balanceDao.toString());
        bankingService.sendMessage(TOPIC_BALANCE, objectMapper.writeValueAsString(balanceDao));
    }

    @GetMapping("/balance")
    public Double getBalance(String accountNumber) {
        return bankingService.getLatestBalance(accountNumber);
    }

    @GetMapping("/transactions")
    public List<TransactionDao> getTransactions(String accountNumber, String type, @DateTimeFormat(pattern = "yyyy-MM-dd") Date startTime, @DateTimeFormat(pattern = "yyyy-MM-dd") Date endTime, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") @Min(value = 1, message = "Page size cannot be less than 1") @Max(value = 20, message = "Page size cannot be greater than 20") int size) {
        Pageable paging = PageRequest.of(page, size);
        return bankingService.findTransactions(accountNumber, type, startTime, endTime, paging);
    }
}
