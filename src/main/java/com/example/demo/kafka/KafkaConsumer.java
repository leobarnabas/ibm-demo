package com.example.demo.kafka;

import com.example.demo.dao.BalanceDao;
import com.example.demo.dao.TransactionDao;
import com.example.demo.service.BankingService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static com.example.demo.constants.AppConstants.TOPIC_BALANCE;
import static com.example.demo.constants.AppConstants.TOPIC_TRANSACTION;

@Component
@Slf4j
public class KafkaConsumer {

    @Autowired
    BankingService bankingService;

    @Autowired
    ObjectMapper objectMapper;

    @KafkaListener(topics = TOPIC_TRANSACTION)
    public void receiveTransactionMessage(ConsumerRecord<String,String> consumerRecord) throws JsonProcessingException {
        log.info("Received transaction message:"+consumerRecord.toString());
        TransactionDao transactionDao = objectMapper.readValue(consumerRecord.value(), TransactionDao.class);
        bankingService.saveTransaction(transactionDao);
    }

    @KafkaListener(topics = TOPIC_BALANCE)
    public void receiveBalanceMessage(ConsumerRecord<String,String> consumerRecord) throws JsonProcessingException {
        log.info("Received balance message:"+consumerRecord.toString());
        BalanceDao balanceDao = objectMapper.readValue(consumerRecord.value(),BalanceDao.class);
        bankingService.saveBalance(balanceDao);
    }
}
