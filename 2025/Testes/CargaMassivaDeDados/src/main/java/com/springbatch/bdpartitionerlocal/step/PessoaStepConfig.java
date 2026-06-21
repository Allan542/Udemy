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
import java.util.List;
import java.util.Locale;

@Configuration
public class PessoaStepConfig {

    Logger log = LoggerFactory.getLogger(PessoaStepConfig.class);

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Step pessoaStep(@Qualifier("pessoaTasklet") Tasklet tasklet,
                           TaskExecutor taskExecutor
    ){
        return stepBuilderFactory.get("pessoaStep")
            .tasklet(tasklet)
            .taskExecutor(taskExecutor)
            .build();
    }

    @Bean
    public Tasklet pessoaTasklet(@Qualifier("appDataSource") DataSource dataSource){
        return (stepContribution, chunkContext) -> {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

            Integer pessoaId = 0;
            while (pessoaId <= 1000000) {
                pessoaId = jdbcTemplate.queryForObject("select pessoa_sq.nextval from dual", Integer.class);

                Faker faker = new Faker(new Locale("pt", "BR"));
                SecureRandom sr = new SecureRandom();

                String nome = faker.name().fullName();
                Integer idade = sr.ints(18, 121).findAny().orElse(18);
                String sexo = sr.nextInt(2) > 0 ? "F" : "M";
                String endereco = faker.address().fullAddress();
                String documento = "";
                for (int j = 0; j < 11; j++) {
                    documento += sr.nextInt(10);
                }
                String profissao = faker.job().title();
                String nacionalidade = faker.country().name();
                String tipo_sanguineo = faker.name().bloodGroup();
                List<Integer> contaList = jdbcTemplate.queryForList("select n_conta from conta", Integer.class);
                int posicaoLista = sr.nextInt(5);
                Integer nr_conta = contaList.get(posicaoLista);

                jdbcTemplate.update("insert into pessoa (pessoa_id," +
                    "                                        nome," +
                    "                                        idade," +
                    "                                        sexo," +
                    "                                        endereco," +
                    "                                        documento," +
                    "                                        profissao," +
                    "                                        nacionalidade," +
                    "                                        tipo_sanguineo," +
                    "                                        nr_conta) " +
                    "                                values (?," +
                    "                                        ?," +
                    "                                        ?," +
                    "                                        ?," +
                    "                                        ?," +
                    "                                        ?," +
                    "                                        ?," +
                    "                                        ?," +
                    "                                        ?," +
                    "                                        ?)", preparedStatementSetter(pessoaId,
                    nome,
                    idade,
                    sexo,
                    endereco,
                    StringUtils.leftPad(documento, 15, '0'),
                    profissao,
                    nacionalidade,
                    tipo_sanguineo,
                    nr_conta));
                log.info("Pessoa de id {} inserida com sucesso", pessoaId);
            }
            return RepeatStatus.FINISHED;
        };
    }

    private PreparedStatementSetter preparedStatementSetter(Integer pessoa_id,
                                                            String nome,
                                                            Integer idade,
                                                            String sexo,
                                                            String endereco,
                                                            String documento,
                                                            String profissao,
                                                            String nacionalidade,
                                                            String tipo_sanguineo,
                                                            Integer nr_conta){
        return ps -> {
            ps.setInt(1, pessoa_id);
            ps.setString(2, nome);
            ps.setInt(3, idade);
            ps.setString(4, sexo);
            ps.setString(5, endereco);
            ps.setString(6, documento);
            ps.setString(7, profissao);
            ps.setString(8, nacionalidade);
            ps.setString(9, tipo_sanguineo);
            ps.setInt(10, nr_conta);
        };
    }

}
