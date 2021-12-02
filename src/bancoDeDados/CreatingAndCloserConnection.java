package bancoDeDados;

import lombok.Getter;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CreatingAndCloserConnection {

    @Getter
    private String CPF;
    private static Connection connection = null;
    private static Statement statement = null;
    @Getter
    private ResultSet resultSet = null;

    public CreatingAndCloserConnection(String CPF) {
        this.CPF = CPF;
        startConnection();
    }

    private void startConnection() {
        try {
            connection = DBConnetion.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("select * from cliente where CPF = " + this.getCPF());

        } catch (SQLException e){
            throw  new DBException(e.getMessage());
        }

    }

    public void resultSetShowRestaurant(){
        try {
            ResultSet rs = statement.executeQuery("Select * from restaurantes");
            while (rs.next()) {
                System.out.println(rs.getString("NOME"));
                }
        }catch (SQLException e){
                throw new DBException(e.getMessage());
            }
        }

    public void closerConnection(){
        if (connection != null){
            try{
                connection.close();
            } catch (SQLException e){
                throw new DBException(e.getMessage());
            }
        }
    }

    public void closerStatement(){
        if (statement != null){
            try{
                statement.close();
            } catch (SQLException e){
                throw new DBException(e.getMessage());
            }
        }
    }

    public void closeResultSet(ResultSet resultSet) {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        }
    }
}