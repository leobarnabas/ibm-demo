package com.example.demo.repository;

import com.example.demo.dao.BalanceDao;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface BalanceRepository extends CrudRepository<BalanceDao,Integer> {
    BalanceDao findByAccountNumber(@Param("accountNumber") String accountNumber);
}
