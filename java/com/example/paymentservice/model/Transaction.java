package com.example.paymentservice.model;

import lombok.Data;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    private String source;
    private String destination;
    private Integer quantity;
    private Double amount;
    private LocalDateTime timestamp;
    private Boolean isPeakHour;
    private Integer durationInMinutes;
    private Double penaltyAmount;
}
