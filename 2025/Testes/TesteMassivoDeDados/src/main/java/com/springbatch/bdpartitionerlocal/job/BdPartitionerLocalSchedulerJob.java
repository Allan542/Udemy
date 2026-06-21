package com.springbatch.bdpartitionerlocal.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
@EnableScheduling
public class BdPartitionerLocalSchedulerJob {

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JobLauncher jobLauncher;
    @Autowired
    private Job job;
    @Autowired
    @Qualifier("appDataSource")
    private DataSource dataSource;

    @Scheduled(cron = "0/30 * * * * *")
    public void schedule() throws Exception {
        JobParameters params = new JobParametersBuilder()
            .addString("jobId", String.valueOf(System.currentTimeMillis()))
            .toJobParameters();
        jobLauncher.run(job, params);
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        int deleteFromPessoa = jdbcTemplate.update("delete from pessoa");
        int deleteFromDadosBancarios = jdbcTemplate.update("delete from dados_bancarios");
        log.info("Dados deletados da tabela de pessoa: {}", deleteFromPessoa);
        log.info("Dados deletados da tabela de dados bancarios: {}", deleteFromDadosBancarios);
    }
}
