package com.allan.payment_service.service;

import com.allan.payment_service.model.Payment;

public interface PaymentService {

    void sendPayment(Payment payment);
}
