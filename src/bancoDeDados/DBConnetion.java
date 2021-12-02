package bancoDeDados;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DBConnetion {

    //Recuperar dados de acesso do arquivo properties.
    private static Properties loadProperties(){
        try(FileInputStream fileInputStream = new FileInputStream("db.properties")){
            Properties properties = new Properties();
            properties.load(fileInputStream);
            return properties;
        } catch (IOException e){
            throw  new DBException(e.getMessage());
        }
    }


    //Criando conexao
    private static Connection connection = null;
    public static Connection getConnection() {
            try {
                if (connection == null) {
                    Properties properties = loadProperties();
                    String url = properties.getProperty("dburl");
                    connection = DriverManager.getConnection(url, properties);
                }
            } catch (SQLException e) {
                throw new DBException(e.getMessage());
            }
        return connection;
    }
}
