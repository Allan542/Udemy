package com.in28minutes.microservices.camelmicroservicea.routes.patterns;

import com.in28minutes.microservices.camelmicroservicea.CurrencyExchange;
import org.apache.camel.Body;
import org.apache.camel.Exchange;
import org.apache.camel.ExchangeProperties;
import org.apache.camel.Headers;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

//@Component
public class EipPatternsRouter extends RouteBuilder {

    @Autowired
    SplitterComponent splitter;

    @Autowired
    DynamicRouterBean dynamicRouterBean;

    @Override
    public void configure() throws Exception {

        // Isso tem que ser definido antes de começar
        getContext().setTracing(true); // Opção para o contexto para setar o "traço"
        errorHandler(deadLetterChannel("activemq:dead-letter-queue")); // se tiver algum erro em alguma message, o deadLetterChannel mandará a mensagem para uma queue

        //Pipeline
        //Content Based Routing - choice()

        //Multicast

//        from("timer:multicast?period=10000")
//            .multicast() // multicast permite que este endpoint seja mandado para múltiplos endpoints
//            .to("log:something1", "log:something2", "log:something3"); // cada um dos processors/endpoints recebe apenas uma cópia da mensagem

        // Splitter Enterprise Integration Pattern - Padrão de separador do camel que pode usar delimitador. No caso, se não for usado, o camel entenderá cada linha de um csv por exemplo, como uma mensagem
//        from("file:files/csv")
//            .unmarshal().csv()
//            .split(body()) // para o split do csv funcionar, é necessário fazer o unmarshal dele, no caso, transformá-lo num objeto csv java. O split separa cada linha em um body diferente
//        .to("activemq:split-queue"); // activemq envia 4 mensagens em vez de uma, cada uma representando uma linha do arquivo csv

        // Message,Message2,Message3 - Separar mensagens por delimitador
        from("file:files/csv")
            .convertBodyTo(String.class)
//            .split(body(),",") // separar pelo delimitador "," (vírgula)
            .split(method(splitter))
            .to("activemq:split-queue");

        // Aggregate Pattern - Contrário do Splitter Pattern, em vez de separar as mensagens, o aggregator juntará as mensagens em uma só
        //Messages => Aggregate => Endpoint
        //to, 3 in messages
        from("file:files/aggregate-json")
            .unmarshal().json(JsonLibrary.Jackson, CurrencyExchange.class) // Obrigatório fazer unmarshal para uma classe java
            .aggregate(simple("${body.to}"), new ArrayListAggregationStrategy()) // Ele só agrupará os valores do campo "to" que forem iguais, caso contrário, separará para serem agrupados com seus respectivos. Ex: USD => USD, INR => INR
            .completionSize(3)
//            .completionTimeout(HIGHEST)
            .to("log:aggregate-json");

        String routingSlip = "direct:endpoint1,direct:endpoint3";
//        String routingSlip = "direct:endpoint1,direct:endpoint2,direct:endpoint3";



        from("direct:endpoint1")
            .wireTap("log:wire-tap") // uma escuta e manda para um additional endpoint
        .to("{{endpoint-for-logging}}");

        from("direct:endpoint2")
        .to("log:directendpoint2");

        from("direct:endpoint3")
        .to("log:directendpoint3");

        // routing slip - é um padrão de rota que decide dinamicamente para quais endpoints serão mandados (mais de um). A diferença entre o slip e o multicast é que o multicast manda por hardcode através do to
        // Já o slip, manda por uma variável passada dentro do método simple, passada dentro do método routing slip
//        from("timer:routingSlip?period=10000")
//            .transform().constant("My Message is Hardcoded")
//            .routingSlip(simple(routingSlip));

        // Dynamic Routing - é um padrão de rota que decide para qual rota mandará de acordo com as regras de negócio

        // Step 1, Step 2, Step 3

        from("timer:dynamicRouting?period={{timePeriod}}")
            .transform().constant("My Message is Hardcoded")
            .dynamicRouter(method(dynamicRouterBean));

        //Endpoint1
        //Endpoint2
        //Endpoint3
    }
}

@Component // Um exemplo de splitter usando hardcode como demonstração de como seria
class SplitterComponent{
    public List<String> splitInput(String body){
        return List.of("ABC", "DEF", "GHI");
    }
}

@Component
class DynamicRouterBean{

    Logger logger = LoggerFactory.getLogger(DynamicRouterBean.class);

    int invocations;

    public String decideTheNextEndpoint(
        @ExchangeProperties Map<String, String> properties,
        @Headers Map<String, String> headers,
        @Body String body
        ){
        logger.info("{} {} {}", properties, headers, body);
        invocations++;

        //Step 1
        if(invocations%3==0){
            return "direct:endpoint1";
        }
        //Step 2
        if(invocations%3==1){
            return "direct:endpoint2,direct:endpoint3";
        }

        // Step 3
        return null;
    }
}