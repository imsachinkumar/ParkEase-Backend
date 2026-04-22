package com.parkease.payment.resource;

import com.parkease.payment.dto.PaymentRequest;
import com.parkease.payment.entity.Payment;
import com.parkease.payment.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentResource {

    private final PaymentService paymentService;

    // POST /api/v1/payments/create
    @PostMapping("/create")
    public ResponseEntity<Payment> create(@Valid @RequestBody PaymentRequest req) {
        Payment payment = Payment.builder()
                .bookingId(req.getBookingId())
                .userId(req.getUserId())
                .amount(req.getAmount())
                .paymentMethod(Payment.PaymentMethod.valueOf(
                        req.getPaymentMethod().toUpperCase()))
                .transactionId(req.getTransactionId())
                .status(Payment.PaymentStatus.PENDING)
                .build();
        return ResponseEntity.ok(paymentService.createPayment(payment));
    }

    // GET /api/v1/payments/{paymentId}
    @GetMapping("/{paymentId}")
    public ResponseEntity<Payment> getById(@PathVariable Integer paymentId) {
        return paymentService.getPaymentById(paymentId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET /api/v1/payments/all
    @GetMapping("/all")
    public ResponseEntity<List<Payment>> getAll() {
        return ResponseEntity.ok(paymentService.getAllPayments());
    }

    // GET /api/v1/payments/user/{userId}
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Payment>> getByUser(@PathVariable Integer userId) {
        return ResponseEntity.ok(paymentService.getPaymentsByUser(userId));
    }

    // GET /api/v1/payments/user/{userId}/success
    @GetMapping("/user/{userId}/success")
    public ResponseEntity<List<Payment>> getSuccessByUser(@PathVariable Integer userId) {
        return ResponseEntity.ok(paymentService.getSuccessfulPaymentsByUser(userId));
    }

    // GET /api/v1/payments/booking/{bookingId}
    @GetMapping("/booking/{bookingId}")
    public ResponseEntity<List<Payment>> getByBooking(@PathVariable Integer bookingId) {
        return ResponseEntity.ok(paymentService.getPaymentsByBooking(bookingId));
    }

    // GET /api/v1/payments/status/{status}
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Payment>> getByStatus(@PathVariable String status) {
        return ResponseEntity.ok(paymentService.getPaymentsByStatus(status));
    }

    // PATCH /api/v1/payments/process/{paymentId}
    @PatchMapping("/process/{paymentId}")
    public ResponseEntity<Payment> process(@PathVariable Integer paymentId) {
        return ResponseEntity.ok(paymentService.processPayment(paymentId));
    }

    // PATCH /api/v1/payments/refund/{paymentId}
    @PatchMapping("/refund/{paymentId}")
    public ResponseEntity<Payment> refund(@PathVariable Integer paymentId) {
        return ResponseEntity.ok(paymentService.refundPayment(paymentId));
    }

    // PATCH /api/v1/payments/status/{paymentId}
    @PatchMapping("/status/{paymentId}")
    public ResponseEntity<Payment> updateStatus(@PathVariable Integer paymentId,
                                                @RequestParam String status) {
        return ResponseEntity.ok(paymentService.updatePaymentStatus(paymentId, status));
    }
}