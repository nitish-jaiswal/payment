package com.example.paymentservice.controller;

import com.example.paymentservice.model.PaymentRequest;
import com.example.paymentservice.model.PaymentResponse;
import com.example.paymentservice.model.Transaction;
import com.example.paymentservice.service.PaymentService;
import com.example.paymentservice.service.TransactionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/payments")
@Api(tags = "Payment Service API")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/process")
    @ApiOperation("Process a new payment")
    public ResponseEntity<PaymentResponse> processPayment(@RequestBody PaymentRequest request) {
        PaymentResponse response = paymentService.processPayment(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/history")
    @ApiOperation("Get last 5 transactions")
    public ResponseEntity<List<Transaction>> getPaymentHistory() {
        List<Transaction> transactions = transactionService.getLastFiveTransactions();
        return ResponseEntity.ok(transactions);
    }
}
