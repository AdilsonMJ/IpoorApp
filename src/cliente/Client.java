package cliente;

import bancoDeDados.DBConnetion;
import bancoDeDados.DBException;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Client implements IntClient{

    Scanner scanner = new Scanner(System.in);
    private boolean validation = false;
    DBConnetion dbConnetion = new DBConnetion();
    Connection connection = DBConnetion.getConnection();
    ResultSet resultSet = null;
    Statement statement = null;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    List<PropertiesClient> propertiesClient = new ArrayList();

    @Override
    public void login(String cpf, String pass) {

        Boolean validated = dbConnetion.validationCPF(cpf, pass);

        if (validated){
            this.validation = true;
            System.out.println("Login realizado com sucesso!");
            try {
                statement = this.connection.createStatement();
                resultSet = statement.executeQuery("select * from cliente where CPF = " + cpf);
                salvarDadosList(resultSet);
            }catch (SQLException e){
                throw new DBException(e.getMessage());
            }
        } else{
            System.out.println("ACESSO NEGADO!");
            System.out.println("**********************************************");
            System.out.println("Criar um novo Usuario: ");
            criarUsuario();
        }

    }

    @Override
    public void criarUsuario() {

            PropertiesClient PV = new PropertiesClient();
            System.out.println("CPF: ");
            PV.setCPF(scanner.next());

            System.out.println("Pass: ");
            PV.setPass(scanner.next());

            System.out.println("Nome: ");
            scanner.nextLine();
            PV.setNome(scanner.nextLine());

            System.out.println("Idade: ");
            PV.setIdade(scanner.nextInt());

            System.out.println("Telefone: ");
            scanner.nextLine();
            PV.setTelefone(scanner.next());

            System.out.println("Endereco: ");
            scanner.nextLine();
            PV.setEndereco1(scanner.nextLine());
            System.out.println("CEP: ");
            PV.setCep(scanner.next());
            System.out.println("Bairro: ");
            scanner.nextLine();
            PV.setBairro(scanner.nextLine());
            System.out.println("Cidade: ");
            PV.setCidade(scanner.nextLine());
            System.out.println("Estado: ");
            PV.setEstado(scanner.nextLine());

            propertiesClient.add(PV);
            dadosUsuario();

            try{
                Connection connection = DBConnetion.getConnection();

                PreparedStatement st = connection.prepareStatement( "INSERT INTO cliente "
                       + "(CPF, PASS, NOME, IDADE, TELEFONE, ENDERECO1, CEP, BAIRRO,  CIDADE, ESTADO)"
                        + "VALUES"
                        + "(?,?,?,?,?,?,?,?,?,?)");
                st.setString(1, PV.CPF);
                st.setString(2, PV.pass);
                st.setString(3, PV.nome);
                st.setInt(4, PV.idade);
                st.setString(5, PV.telefone);
                st.setString(6, PV.endereco1);
                st.setString(7, PV.cep);
                st.setString(8, PV.bairro);
                st.setString(9, PV.cidade);
                st.setString(10, PV.estado);

                st.executeUpdate();


            } catch (SQLException e){
                throw new DBException(e.getMessage());
            }


    }


    @Override
    public void dadosUsuario() {
        for(PropertiesClient pv : propertiesClient){
            System.out.println(pv.nome);
            System.out.println(pv.CPF);
            System.out.println(pv.idade);
            System.out.println(pv.telefone);
            System.out.println(pv.cep);
            System.out.println(pv.bairro);
            System.out.println(pv.cidade);
            System.out.println(pv.estado);
        }

    }

    @Override
    public void showRestaurantes() {
        try {
            Statement statement = this.connection.createStatement();
            ResultSet rs = statement.executeQuery("Select * from restaurantes");
            while (rs.next()) {
                System.out.println(rs.getString("NOME"));
            }
        }catch (SQLException e){
            throw new DBException(e.getMessage());
        }
    }

    @Override
    public void fazerPedido() {

    }

    @Override
    public void consultarOrdens() {

    }

    private void salvarDadosList(ResultSet resultSet){

        PropertiesClient PV = new PropertiesClient();
        try {
            while (resultSet.next()){
                PV.setCPF(resultSet.getString("CPF"));
                PV.setNome(resultSet.getString("NOME"));
                PV.setIdade(resultSet.getInt("IDADE"));
                PV.setTelefone(resultSet.getString("TELEFONE"));
                PV.setEndereco1(resultSet.getString("ENDERECO1"));
                PV.setCep(resultSet.getString("CEP"));
                PV.setBairro(resultSet.getString("BAIRRO"));
                PV.setCidade(resultSet.getString("CIDADE"));
                PV.setEstado(resultSet.getString("ESTADO"));
            }
            propertiesClient.add(PV);
        } catch (SQLException e){
            throw new DBException(e.getMessage());
        }
    }
}
