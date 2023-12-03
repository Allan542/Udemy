package com.udemy.contasbancariasjob.writer;

import com.udemy.contasbancariasjob.dominio.Conta;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

@Component
public class ContasBancariasWriter implements ItemWriter<Conta> {

    @Qualifier("appDataSource")
    @Autowired
    private DataSource dataSource;

    @Override
    public void write(List<? extends Conta> list) throws Exception {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "insert into conta (tipo, limite, cliente_id) values (?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                for (Conta conta : list) {
                    statement.setString(1, String.valueOf(conta.getTipoConta()));
                    statement.setBigDecimal(2, conta.getLimite());
                    statement.setString(3, conta.getClientId());

                    statement.execute();
                    System.out.println(conta + " - CONTA CRIADA");
                }
            }
        }
    }
}
