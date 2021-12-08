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


    public Boolean validationCPF(String CPF, String PASS){

        Boolean validated = false;

        try{
            Connection conn = DBConnetion.getConnection();
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from cliente where CPF = " + CPF + " and PASS = " + PASS);

            while (resultSet.next()){
                validated = true;
            }

        }catch (SQLException e){
            throw new DBException(e.getMessage());
        }
        return validated;

    };

    public Boolean validationCNPJ(String CNPJ, String PASS){
        Boolean validationCNPJ = false;

        try {
            Connection connection = DBConnetion.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM RESTAURANTES WHERE CNPJ = " + CNPJ + " AND PASS = " + PASS);

            while (resultSet.next()){
                validationCNPJ = true;
            }
        } catch (SQLException e){
            throw new DBException(e.getMessage());
        }

        return validationCNPJ;
    };
}
