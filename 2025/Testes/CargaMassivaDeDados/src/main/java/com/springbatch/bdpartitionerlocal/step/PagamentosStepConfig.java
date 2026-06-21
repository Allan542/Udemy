package com.springbatch.bdpartitionerlocal.step;

import com.github.javafaker.Faker;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;

import javax.sql.DataSource;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Configuration
public class PagamentosStepConfig {

    Logger log = LoggerFactory.getLogger(PagamentosStepConfig.class);

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Step pagamentosStep(@Qualifier("pagamentosTasklet") Tasklet tasklet,
                               TaskExecutor taskExecutor
    ){
        return stepBuilderFactory.get("pagamentosStep")
            .tasklet(tasklet)
            .taskExecutor(taskExecutor)
            .build();
    }

    @Bean
    public Tasklet pagamentosTasklet(@Qualifier("appDataSource") DataSource dataSource){
        return (stepContribution, chunkContext) -> {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

            int pagamentoInserido = 0, i = 0;
            Integer pagamentosId = 0;
            while (i <= 1000000) {
                SecureRandom sr = new SecureRandom();
                Faker faker = new Faker(new Locale("pt", "BR"));
                int totalNumeroParcela = sr.nextInt(37);
                Integer pessoaId = sr.nextInt(1000001);

                for (int numeroParcela = 1; numeroParcela <= totalNumeroParcela; numeroParcela++){
                    pagamentosId = jdbcTemplate.queryForObject("select pagamentos_sq.nextval from dual", Integer.class);

                    Double valor = faker.number().randomDouble(2, 1, 2000);
                    Double taxa = faker.number().randomDouble(2, 1, 10);
                    String statusPagamento;
                    if(sr.nextInt(2) > 0){
                        statusPagamento = "COBRADA";
                    } else {
                        statusPagamento = "PENDENTE";
                    }
                    String numeroCartao = faker.finance().creditCard();

                    pagamentoInserido += jdbcTemplate.update("insert into pagamentos (pagamento_id," +
                        "                                                                 valor," +
                        "                                                                 taxa," +
                        "                                                                 numero_parcela," +
                        "                                                                 status_pagamento," +
                        "                                                                 numero_cartao," +
                        "                                                                 pessoa_id) " +
                        "                                                         values (?," +
                        "                                                                 ?," +
                        "                                                                 ?," +
                        "                                                                 ?," +
                        "                                                                 ?," +
                        "                                                                 ?," +
                        "                                                                 ?)",
                        preparedStatementSetter(pagamentosId,
                                                valor,
                                                taxa,
                                                numeroParcela,
                                                statusPagamento,
                                                numeroCartao,
                                                pessoaId));
                    log.info("Pagamento id {} inserida com sucesso", pagamentoInserido);
                }
                i += totalNumeroParcela;
                log.info("Pessoa com id {} agora possui pagamentos cadastrados", pessoaId);
            }
            return RepeatStatus.FINISHED;
        };
    }

    private PreparedStatementSetter preparedStatementSetter(Integer pagamento_id,
                                                            Double valor,
                                                            Double taxa,
                                                            Integer numero_parcela,
                                                            String status_pagamento,
                                                            String numero_cartao,
                                                            Integer pessoa_id){
        return ps -> {
            ps.setInt(1, pagamento_id);
            ps.setDouble(2, valor);
            ps.setDouble(3, taxa);
            ps.setInt(4, numero_parcela);
            ps.setString(5, status_pagamento);
            ps.setString(6, numero_cartao);
            ps.setInt(7, pessoa_id);
        };
    }
}
