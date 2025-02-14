package com.example.paymentservice.service;

import com.example.paymentservice.model.Transaction;
import com.example.paymentservice.model.PaymentRequest;
import com.example.paymentservice.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    private static final int PENALTY_THRESHOLD_MINUTES = 90;
    private static final double PENALTY_RATE = 5.0;

    public double createTransaction(PaymentRequest request, double amount) {
        Transaction transaction = new Transaction();
        transaction.setPaymentMethod(request.getPaymentMethod());
        transaction.setSource(request.getSource());
        transaction.setDestination(request.getDestination());
        transaction.setQuantity(request.getQuantity());
        transaction.setAmount(amount);
        transaction.setTimestamp(LocalDateTime.now());
        transaction.setIsPeakHour(isPeakHour());

        // Simulate journey duration
        int duration = 100; // Example duration
        transaction.setDurationInMinutes(duration);

        double penaltyAmount = calculatePenalty(duration);
        transaction.setPenaltyAmount(penaltyAmount);

        transactionRepository.save(transaction);
        return penaltyAmount;
    }

    private boolean isPeakHour() {
        LocalDateTime now = LocalDateTime.now();
        int hour = now.getHour();
        return (hour >= 8 && hour <= 10) || (hour >= 17 && hour <= 19);
    }

    private double calculatePenalty(int durationInMinutes) {
        if (durationInMinutes > PENALTY_THRESHOLD_MINUTES) {
            int extraMinutes = durationInMinutes - PENALTY_THRESHOLD_MINUTES;
            return extraMinutes * PENALTY_RATE;
        }
        return 0.0;
    }

    public List<Transaction> getLastFiveTransactions() {
        return transactionRepository.findTop5ByOrderByTimestampDesc();
    }
}
