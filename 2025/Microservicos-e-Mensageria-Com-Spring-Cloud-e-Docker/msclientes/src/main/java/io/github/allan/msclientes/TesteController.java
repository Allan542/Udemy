package io.github.allan.msclientes;

import oracle.jdbc.OracleType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.*;

@Controller
@RequestMapping("/teste")
public class TesteController {


    @GetMapping
    public ResponseEntity teste() throws SQLException {
        String teste = null;
        try (Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "allan", "allan123");
             CallableStatement statement = connection.prepareCall("DECLARE\n" +
                 "    v_cliente cliente%rowtype;\n" +
//                 "    codigo number;\n" +
//                 "    nome varchar2(50);\n" +
                 "BEGIN\n" +
                 "    allan.teste(20, v_cliente);\n" +
                 "    ? := v_cliente.codigo;\n" +
                 "    ? := v_cliente.nome;\n" +
//                 "    dbms_output.put_line(codigo);\n" +
                 "END;")) {
            statement.registerOutParameter(1, OracleType.NUMBER);
            statement.registerOutParameter(2, OracleType.VARCHAR2);
            statement.execute();
            System.out.println(statement.getInt(1));
            System.out.println(statement.getString(2));
        }
        return ResponseEntity.ok("Ok");
    }
}
