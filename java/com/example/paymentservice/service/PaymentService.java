package com.example.paymentservice.service;

import com.example.paymentservice.model.PaymentRequest;
import com.example.paymentservice.model.PaymentResponse;
import com.example.paymentservice.model.PaymentMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class PaymentService {

    @Autowired
    private TransactionService transactionService;

    private static final double BASE_FARE = 10.0;
    private static final double METRO_CARD_DISCOUNT = 0.10;
    private static final double PEAK_HOUR_MULTIPLIER = 1.5;

    public PaymentResponse processPayment(PaymentRequest request) {
        double amount = calculateFare(request);

        // Create and save transaction
        double penaltyAmount = transactionService.createTransaction(request, amount);

        PaymentResponse response = new PaymentResponse();
        response.setStatus("ticket_payment_success");
        response.setAmount(amount);
        response.setPenaltyAmount(penaltyAmount);

        return response;
    }

    private double calculateFare(PaymentRequest request) {
        double baseFare = BASE_FARE * request.getQuantity();

        if (request.getPaymentMethod() == PaymentMethod.METRO_CARD) {
            baseFare = baseFare * (1 - METRO_CARD_DISCOUNT);
        }

        if (isPeakHour()) {
            baseFare = baseFare * PEAK_HOUR_MULTIPLIER;
        }

        return baseFare;
    }

    private boolean isPeakHour() {
        LocalDateTime now = LocalDateTime.now();
        int hour = now.getHour();
        return (hour >= 8 && hour <= 10) || (hour >= 17 && hour <= 19);
    }
}
