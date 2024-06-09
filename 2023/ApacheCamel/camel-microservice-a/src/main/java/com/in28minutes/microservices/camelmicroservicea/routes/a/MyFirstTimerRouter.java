package com.in28minutes.microservices.camelmicroservicea.routes.a;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

//@Component
public class MyFirstTimerRouter extends RouteBuilder {

    @Autowired
    private GetCurrentTimeBean getCurrentTimeBean;

    @Autowired
    private SimpleLoggingProcessingComponent loggingComponent;

    @Override
    public void configure() throws Exception {
        // Esta sequencia que define uma rota
        // timer no momento, queue (fila) - endpoint
        // transformation
        // log no momento, database - endpoint
        // Exchange[ExchangePattern: InOnly, BodyType: null, Body: [Body is null]]
        from("timer:first-timer") // null // timer endpoint. queue - Produz uma "mensagem", começo do endpoint
            .log("${body}") // null
            .transform().constant("My Constant Message")
            .log("${body}") // My Constant Message - mostrando que o corpo da mensagem foi afetado por causa da Transformation
//          .transform().constant("Time now is " + LocalDateTime.now())
            //.bean("getCurrentTimeBean") // Quando voce pega uma mensagem da queue, voce vai fazer transformações e depois para o banco de dados

            // Há duas tipos de operações que podem ser feitas para rotas específicas
            // Processing - Serve para quando uma operação não fará alterações no corpo da mensagem. Pode ser usado o método process() ou bean()
            // Transformation - Serve para quando uma operação fará alterações no corpo da mensagem. Pode ser usado o método transform() ou bean()

            // bean pode ser usado tanto para fazer transformations, que é usar métodos com retorno para alterar o body, como pode fazer process, que são métodos void que não alteram o body
            .bean(getCurrentTimeBean, "getCurrentTime") // O bean acima é uma má prática. Quando se tem só um método na classe, não é necessário passar o nome do método
            .log("${body}") // Time now is 2023-06-03T16:22:37.661470300
            .process(new SimpleLoggingProcessor())
            .bean(loggingComponent)
        .to("log:first-timer"); // database
    }
}

@Component
class GetCurrentTimeBean {
    // Uma outra diferença que pode ser notada entre transforming e process, é que o tipo do retorno, que neste caso é do tipo string,
    // ou seja, fará uma alteração no body que era "My Constant Message"
    public String getCurrentTime() {
        return "Time now is " + LocalDateTime.now();
    }
}

@Component
class SimpleLoggingProcessingComponent {

    private Logger logger = LoggerFactory.getLogger(SimpleLoggingProcessingComponent.class);

    // Métodos do tipo void que fazem uma "Wide Back", são métodos que apenas fazem processamento e portanto, não fazem alteração do body da message na hora do retorno
    public void process(String message) {

        logger.info("SimpleLoggingProcessingComponent {}", message);
}
}

class SimpleLoggingProcessor implements Processor {

    private Logger logger = LoggerFactory.getLogger(SimpleLoggingProcessor.class);

    @Override
    public void process(Exchange exchange) throws Exception {

        logger.info("Simple Logging Processor {}", exchange.getMessage().getBody());
    }
}
