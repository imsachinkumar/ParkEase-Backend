package com.parkease.payment.service.impl;

import com.parkease.payment.entity.Payment;
import com.parkease.payment.repository.PaymentRepository;
import com.parkease.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    @Override
    public Payment createPayment(Payment payment) {
        if (paymentRepository.existsByBookingIdAndStatus(
                payment.getBookingId(), Payment.PaymentStatus.SUCCESS)) {
            throw new RuntimeException("Payment already done for booking: "
                    + payment.getBookingId());
        }
        if (payment.getTransactionId() == null) {
            payment.setTransactionId("TXN-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        }
        return paymentRepository.save(payment);
    }

    @Override
    public Optional<Payment> getPaymentById(Integer paymentId) {
        return paymentRepository.findById(paymentId);
    }

    @Override
    public List<Payment> getPaymentsByUser(Integer userId) {
        return paymentRepository.findByUserId(userId);
    }

    @Override
    public List<Payment> getPaymentsByBooking(Integer bookingId) {
        return paymentRepository.findByBookingId(bookingId);
    }

    @Override
    public List<Payment> getPaymentsByStatus(String status) {
        return paymentRepository.findByStatus(
                Payment.PaymentStatus.valueOf(status.toUpperCase()));
    }

    @Override
    public List<Payment> getSuccessfulPaymentsByUser(Integer userId) {
        return paymentRepository.findByUserIdAndStatus(
                userId, Payment.PaymentStatus.SUCCESS);
    }

    @Override
    public Payment updatePaymentStatus(Integer paymentId, String status) {
        Payment existing = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found: " + paymentId));
        existing.setStatus(Payment.PaymentStatus.valueOf(status.toUpperCase()));
        return paymentRepository.save(existing);
    }

    @Override
    public Payment processPayment(Integer paymentId) {
        Payment existing = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found: " + paymentId));
        existing.setStatus(Payment.PaymentStatus.SUCCESS);
        existing.setPaidAt(LocalDateTime.now());
        return paymentRepository.save(existing);
    }

    @Override
    public Payment refundPayment(Integer paymentId) {
        Payment existing = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found: " + paymentId));
        if (existing.getStatus() != Payment.PaymentStatus.SUCCESS) {
            throw new RuntimeException("Only successful payments can be refunded");
        }
        existing.setStatus(Payment.PaymentStatus.REFUNDED);
        return paymentRepository.save(existing);
    }

    @Override
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }
}