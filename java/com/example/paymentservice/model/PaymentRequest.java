package com.example.paymentservice.model;

import lombok.Data;

@Data
public class PaymentRequest {
    private PaymentMethod paymentMethod;
    private String source;
    private String destination;
    private Integer quantity;
}