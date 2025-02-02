package com.allan.payment_service.service.impl;

import com.allan.payment_service.model.Payment;
import com.allan.payment_service.service.PaymentService;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@Service
@Log4j2
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private KafkaTemplate<String, Serializable> kafkaTemplate;

    @SneakyThrows
    @Override
    public void sendPayment(Payment payment) {
        log.info("Recebi o pagamento {}", payment);
        Thread.sleep(1000);

        log.info("Enviando pagamento");
        kafkaTemplate.send("payment-topic", payment);
    }
}
