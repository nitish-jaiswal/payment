package com.example.paymentservice.repository;

import com.example.paymentservice.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findTop5ByOrderByTimestampDesc();
}