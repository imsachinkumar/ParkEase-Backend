package com.parkease.payment.service;

import com.parkease.payment.entity.Payment;
import java.util.List;
import java.util.Optional;

public interface PaymentService {
    Payment createPayment(Payment payment);
    Optional<Payment> getPaymentById(Integer paymentId);
    List<Payment> getPaymentsByUser(Integer userId);
    List<Payment> getPaymentsByBooking(Integer bookingId);
    List<Payment> getPaymentsByStatus(String status);
    List<Payment> getSuccessfulPaymentsByUser(Integer userId);
    Payment updatePaymentStatus(Integer paymentId, String status);
    Payment processPayment(Integer paymentId);
    Payment refundPayment(Integer paymentId);
    List<Payment> getAllPayments();
}