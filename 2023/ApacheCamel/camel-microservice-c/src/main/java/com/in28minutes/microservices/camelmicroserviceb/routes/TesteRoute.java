package com.in28minutes.microservices.camelmicroserviceb.routes;

import com.in28minutes.microservices.camelmicroserviceb.domains.TesteAuthResponse;
import com.in28minutes.microservices.camelmicroserviceb.domains.TesteConsultaResponse;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

@Component
public class TesteRoute extends RouteBuilder {


    @Override
    public void configure() throws Exception {

//        from("timer:first-timer?period=100000")
//            .to("http://localhost:8080/hello")
//            .log("${body}");

        from("timer:first-timer?period=1000000")
            .routeId("timer-teste")
//            .convertBodyTo(TesteAuthRequest.class)
//            .removeHeaders("*")
            .setHeader(Exchange.HTTP_METHOD, constant("POST"))
            .setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
            .setBody(simple("{\n" +
                "    \"login\": \"allan.carlos@voll.med\",\n" +
                "    \"senha\": \"123456\"\n" +
                "}"))
//            .unmarshal().json(JsonLibrary.Jackson, TesteAuthRequest.class)
//            .process(exchange -> {
//                TesteAuthRequest body = exchange.getIn().getBody(TesteAuthRequest.class);
//                System.out.println(body.getLogin());
//                exchange.getIn().setBody(body);
//            })
//            .marshal().json(JsonLibrary.Jackson)
            .log("${body}")
            .log("${headers}")
            .toD("http://localhost:8080/login")
            .unmarshal().json(JsonLibrary.Jackson, TesteAuthResponse.class)
            .log("${body.token}")
            .to("direct:outro-rest");

        from("direct:outro-rest")
            .setHeader("Authorization", simple("Bearer ${body.token}"))
            .setHeader(Exchange.HTTP_METHOD, constant("GET"))
            .setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
            .setProperty("temMaisDados", constant(Integer.parseInt("1")))
            .toD("http://localhost:8080/consultas")
            .log("${body}")
            .unmarshal().json(JsonLibrary.Jackson, TesteConsultaResponse.class)
            .loop(simple("${body.content.size}"))
            .log("${exchangeProperty.CamelLoopIndex}")
            .log("${body.content[${exchangeProperty.CamelLoopIndex}]}")
//            .setProperty("temMaisDados", constant(Boolean.FALSE))
            .end();
    }
}
