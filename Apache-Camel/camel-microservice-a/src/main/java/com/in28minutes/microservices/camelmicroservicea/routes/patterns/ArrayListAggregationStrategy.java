package com.in28minutes.microservices.camelmicroservicea.routes.patterns;

import org.apache.camel.AggregationStrategy;
import org.apache.camel.Exchange;

import java.util.ArrayList;

public class ArrayListAggregationStrategy implements AggregationStrategy {
    public ArrayListAggregationStrategy() {
    }

    @Override
    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
        Object newBody = newExchange.getIn().getBody();
        ArrayList<Object> list;
        if (oldExchange == null) { // Se é o primeiro exchange sem modificação
            list = new ArrayList<Object>();
            list.add(newBody);
            newExchange.getIn().setBody(list); // Cria a primeira newExchange e retorna a mesma
            return newExchange;
        } else { // Se é uma exchange já modificada que foi recebida no oldExchange
            list = oldExchange.getIn().getBody(ArrayList.class);
            list.add(newBody); // adicionará a newExchange na lista do oldExchange e retornará todos os valores
            return oldExchange;
        }
    }
}