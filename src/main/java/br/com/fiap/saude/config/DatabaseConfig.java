package br.com.fiap.saude.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfig {

    private static final String URL  =
            "jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL";
    private static final String USER =
            System.getenv("DB_USER");
    private static final String PASS =
            System.getenv("DB_PASS");

    public static Connection getConnection() throws SQLException {
        if (USER == null || PASS == null) {
            throw new SQLException("DB_USER ou DB_PASS não configurados nas variáveis de ambiente da aplicação");
        }
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
