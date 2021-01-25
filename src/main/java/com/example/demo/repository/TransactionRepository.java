package com.example.demo.repository;

import com.example.demo.dao.TransactionDao;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface TransactionRepository extends CrudRepository<TransactionDao, Integer>, JpaSpecificationExecutor<TransactionDao> {
}
