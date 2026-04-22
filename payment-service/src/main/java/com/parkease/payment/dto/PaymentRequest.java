package com.parkease.payment.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class PaymentRequest {

    @NotNull(message = "Booking ID is required")
    private Integer bookingId;

    @NotNull(message = "User ID is required")
    private Integer userId;

    @NotNull(message = "Amount is required")
    private Double amount;

    @NotBlank(message = "Payment method is required: CASH, UPI, CARD, NET_BANKING, WALLET")
    private String paymentMethod;

    private String transactionId;
}