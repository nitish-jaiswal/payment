package com.example.paymentservice.model;

import lombok.Data;

@Data
public class PaymentResponse {
    private String status;
    private Double amount;
    private Double penaltyAmount;
}
