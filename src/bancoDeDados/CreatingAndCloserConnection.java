package bancoDeDados;

import lombok.Getter;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CreatingAndCloserConnection {
    Statement statement = null;

    private void startConnection(String CPF) {
        try {
           Connection connection = DBConnetion.getConnection();
           statement = connection.createStatement();
           ResultSet resultSet = statement.executeQuery("select * from cliente where CPF = " + CPF);

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

    public Boolean validation(String CPF, String PASS){

        Boolean validated = false;

        try{
            Connection conn = DBConnetion.getConnection();
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from cliente where CPF = " + CPF + " and PASS = " + PASS);

            while (resultSet.next()){
                System.out.println(resultSet.getString("CPF") + " - Pass " + resultSet.getString("PASS"));
                validated = true;
            }

        }catch (SQLException e){
            throw new DBException(e.getMessage());
        }
        return validated;
    };


    public void closerConnection(Connection connection){
        if (connection != null){
            try{
                connection.close();
            } catch (SQLException e){
                throw new DBException(e.getMessage());
            }
        }
    }

    public void closerStatement(Statement statement){
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