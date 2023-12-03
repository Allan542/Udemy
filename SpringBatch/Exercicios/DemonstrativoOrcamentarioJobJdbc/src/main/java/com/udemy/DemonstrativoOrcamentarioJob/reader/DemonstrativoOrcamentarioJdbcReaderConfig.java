package com.udemy.DemonstrativoOrcamentarioJob.reader;

import com.udemy.DemonstrativoOrcamentarioJob.dominio.DemonstrativoLancamento;
import com.udemy.DemonstrativoOrcamentarioJob.dominio.DemonstrativoOrcamentario;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@Configuration
public class DemonstrativoOrcamentarioJdbcReaderConfig {

    @Bean
    public JdbcCursorItemReader<DemonstrativoOrcamentario> jdbcCursorItemReader(
        @Qualifier("appDataSource") DataSource dataSource
        ){
        return new JdbcCursorItemReaderBuilder<DemonstrativoOrcamentario>()
            .name("jdbcCursorItemReader")
            .dataSource(dataSource)
            .sql("select * from lancamento")
            .rowMapper(rowMapper())
            .build();
    }

    private RowMapper<DemonstrativoOrcamentario> rowMapper() {
        return (rs, rowNum) -> demonstrativoRowMapper(rs);
    }

    private static DemonstrativoOrcamentario demonstrativoRowMapper(ResultSet rs) throws SQLException {
        DemonstrativoOrcamentario demonstrativoOrcamentario = new DemonstrativoOrcamentario();
        demonstrativoOrcamentario.setDemonstrativoList(new ArrayList<>());
        demonstrativoOrcamentario.setCodigoNaturezaDespesa(rs.getString("codigoNaturezaDespesa"));
        demonstrativoOrcamentario.setDescricaoNaturezaDespesa(rs.getString("descricaoNaturezaDespesa"));

        DemonstrativoLancamento demonstrativoLancamento = new DemonstrativoLancamento();
        demonstrativoLancamento.setDescricaoLancamento(rs.getString("descricaoLancamento"));
        demonstrativoLancamento.setDataLancamento(rs.getString("dataLancamento"));
        demonstrativoLancamento.setValorLancamento(new BigDecimal(rs.getString("valorLancamento")));

        demonstrativoOrcamentario.getDemonstrativoList().add(demonstrativoLancamento);

        return demonstrativoOrcamentario;
    }
}
