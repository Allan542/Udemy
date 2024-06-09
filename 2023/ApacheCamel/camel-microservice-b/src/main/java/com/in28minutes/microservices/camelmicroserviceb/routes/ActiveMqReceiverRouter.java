package com.in28minutes.microservices.camelmicroserviceb.routes;

import com.in28minutes.microservices.camelmicroserviceb.CurrencyExchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.converter.crypto.CryptoDataFormat;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.*;
import java.security.cert.CertificateException;

@Component
public class ActiveMqReceiverRouter extends RouteBuilder {

    @Autowired
    private MyCurrencyExchangeProcessor myCurrencyExchangeProcessor;
    @Autowired
    private MyCurrencyExchangeTransformer myCurrencyExchangeTransformer;

    @Override
    public void configure() throws Exception {
        //JSON
        //CurrencyExchange
        //{ "id": 1001, "from": "EUR", "to": "INR",  "conversionMultiple": 80  }

        from("activemq:my-activemq-queue")
            .unmarshal(createEncryptor())
//                .unmarshal()
//                .json(JsonLibrary.Jackson, CurrencyExchange.class)
//                .bean(myCurrencyExchangeProcessor)
//                .bean(myCurrencyExchangeTransformer)
        .to("log:received-message-from-active-mq");

        from("activemq:my-activemq-xml-queue")
                .unmarshal()
                .jacksonXml(CurrencyExchange.class)
        .to("log:received-message-from-active-mq");

        from("activemq:split-queue")
            .to("log:received-message-from-active-mq");
    }

    private CryptoDataFormat createEncryptor() throws KeyStoreException, IOException, NoSuchAlgorithmException,
        CertificateException, UnrecoverableKeyException {
        KeyStore keyStore = KeyStore.getInstance("JCEKS");
        ClassLoader classLoader = getClass().getClassLoader();
        keyStore.load(classLoader.getResourceAsStream("myDesKey.jceks"), "someKeystorePassword".toCharArray());
        Key sharedKey = keyStore.getKey("myDesKey", "someKeyPassword".toCharArray());

        CryptoDataFormat sharedKeyCrypto = new CryptoDataFormat("DES", sharedKey);
        return sharedKeyCrypto;
    }
}

@Component
class MyCurrencyExchangeProcessor {

    Logger logger = LoggerFactory.getLogger(MyCurrencyExchangeProcessor.class);

    public void processMessage(CurrencyExchange currencyExchange) {

        logger.info("Do some processing with currencyExchange.getConversionMultiple() value which is {}",
                currencyExchange.getConversionMultiple());
    }
}

@Component
class MyCurrencyExchangeTransformer {

    Logger logger = LoggerFactory.getLogger(MyCurrencyExchangeTransformer.class);

    // Se o valor foi alterado em um bean, então ele deve ser retornado, pois ele está usando o padrão transformer em vez do processor
    // Porém, se o valor foi alterado num processor, ele se manterá alterado mesmo fora desta função, no caso, a nível de escopo de um endpoint
    public CurrencyExchange processMessage(CurrencyExchange currencyExchange) {

        currencyExchange.setConversionMultiple(
                currencyExchange.getConversionMultiple().multiply(BigDecimal.TEN));

        return currencyExchange;
    }
}
