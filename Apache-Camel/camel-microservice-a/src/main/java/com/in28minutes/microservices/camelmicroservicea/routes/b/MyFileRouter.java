package com.in28minutes.microservices.camelmicroservicea.routes.b;

import org.apache.camel.*;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

//@Component
public class MyFileRouter extends RouteBuilder {
    @Autowired
    private DeciderBean deciderBean;

    @Override
    public void configure() throws Exception { // manipular arquivos com camel só precisa do from e to

        //Pipeline pattern

        from("file:files/input")
//            .pipeline() é um pattern que é padrão do camel
            .routeId("Files-Input-Route")
            .transform().body(String.class)
            .choice() // Content Based Routing - baseado no conteúdo ou no header da mensagem, pode ser direcionado para caminhos diferentes, este é o significado desse pattern usando choice-when
                .when(simple("${file:ext} == 'xml'")) // ends with também funciona. "Simple" expressions precisam sempre estar dentro de um método chamado "simple()"
                    .log("XML FILE")
//                .when(simple("${body} contains 'USD'"))
                .when(method(deciderBean))
                .log("Not an XML FILE BUT contains 'USD'")
                .otherwise()
                    .log("Not an XML FILE")
            .end()

//        .to("direct://log-file-values")
        .to("file:files/output");
        // Link abaixo são para os camel "simples" que podem ser usados em um log ou numa condicional para manipular/validar dados de strings, arquivos etc, usar condicionais, dentre outras coisas

        from("direct:log-file-values")
            .log("${messageHistory} ${file:absolute.path}")
            .log("${file:name} ${file:name.ext} ${file:name.noext} ${file:onlyname}")
            .log("${file:onlyname.noext} ${file:parent} ${file:path} ${file:absolute}")
            .log("${file:size} ${file:modified}")
            .log("${routeId} ${camelId} ${body}"); // https://camel.apache.org/components/3.20.x/languages/simple-language.html
    }
}

@Component
class DeciderBean {

    Logger logger = LoggerFactory.getLogger(DeciderBean.class);

    public boolean isThisConditionMet(@Body String body,
                                      @Headers Map<String, String> headers, // Annotations do camel para mostrar alguns detalhes, como headers, body, exchanges etc
                                      @ExchangeProperties Map<String, String> exchangeProperties) { // Também, existem annotations que permitem que seja possível manipular uma Exchange ou uma Message
        logger.info("DeciderBean {} {} {}", body, headers, exchangeProperties);
        return true;
    }
}
