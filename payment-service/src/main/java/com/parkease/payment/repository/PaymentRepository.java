package com.parkease.payment.repository;

import com.parkease.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {

    List<Payment> findByUserId(Integer userId);
    List<Payment> findByBookingId(Integer bookingId);
    List<Payment> findByStatus(Payment.PaymentStatus status);
    List<Payment> findByUserIdAndStatus(Integer userId, Payment.PaymentStatus status);
    Optional<Payment> findByTransactionId(String transactionId);
    boolean existsByBookingIdAndStatus(Integer bookingId, Payment.PaymentStatus status);
}