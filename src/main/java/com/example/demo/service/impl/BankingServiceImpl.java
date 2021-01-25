package com.example.demo.service.impl;

import com.example.demo.dao.BalanceDao;
import com.example.demo.dao.TransactionDao;
import com.example.demo.repository.BalanceRepository;
import com.example.demo.repository.TransactionRepository;
import com.example.demo.service.BankingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class BankingServiceImpl implements BankingService {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    BalanceRepository balanceRepository;

    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    public List<TransactionDao> findTransactions(String accountNumber, String type, Date startTime, Date endTime, Pageable pageable) {
        log.info("Account Number:" + accountNumber + ", Type:" + type + ", StartTime:" + startTime + ", EndTime:" + endTime);

        Page page = transactionRepository.findAll(new Specification<TransactionDao>() {

            @Override
            public Predicate toPredicate(Root<TransactionDao> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (accountNumber != null) {
                    predicates.add((criteriaBuilder.equal(root.get("accountNumber"), accountNumber)));
                }
                if (type != null) {
                    predicates.add(criteriaBuilder.equal(root.get("type"), type));
                }
                if (startTime != null && endTime != null) {
                    predicates.add(criteriaBuilder.between(root.get("transactionTs"), startTime, endTime));
                }

                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        }, pageable);

        page.getTotalElements();
        page.getTotalPages();
        return page.getContent();
    }

    public Double getLatestBalance(String accountNumber) {
        Double balance = 0.0;
        BalanceDao balanceDao = balanceRepository.findByAccountNumber(accountNumber);
        if (balanceDao != null) {
            log.info("Found balance:" + balanceDao.toString());
            balance = balanceDao.getBalance();
        }
        return balance;
    }

    public void saveTransaction(TransactionDao transactionDao) {
        transactionRepository.save(transactionDao);
    }

    public void saveBalance(BalanceDao balanceDao) {
        balanceRepository.save(balanceDao);
    }

    @Override
    public void sendMessage(String topic, String payload) {
        log.info("Topic:" + topic + ", payload:" + payload);
        kafkaTemplate.send(topic, payload);
    }
}
