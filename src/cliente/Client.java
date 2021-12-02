package cliente;

import bancoDeDados.CreatingAndCloserConnection;
import bancoDeDados.DBException;
import com.sun.source.tree.IfTree;
import lombok.Getter;
import org.w3c.dom.ls.LSOutput;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Client implements IntClient{

    Scanner scanner = new Scanner(System.in);

    private String CPF;
    CreatingAndCloserConnection creatingAndCloserConnection = null;
    ResultSet resultSet = null;
    List<PropertiesVendedor> propertiesVendedors = new ArrayList();

    public Client(String CPF, String PASS) {
        this.CPF = CPF;
        creatingAndCloserConnection = new CreatingAndCloserConnection();

        Boolean validated = creatingAndCloserConnection.validation(CPF, PASS);
        if (validated){
            System.out.println("APROVADO");
        } else{
            System.out.println("ACESSO NEGADO!");
        }



    }

    @Override
    public void criarUsuario() {

            PropertiesVendedor PV = new PropertiesVendedor();
            System.out.println("CPF: ");
            PV.setCPF(scanner.next());

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

            propertiesVendedors.add(PV);
            dadosUsuario();

    }

    @Override
    public void dadosUsuario() {
        for(PropertiesVendedor pv : propertiesVendedors){
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
        creatingAndCloserConnection.resultSetShowRestaurant();
    }

    @Override
    public void fazerPedido() {

    }

    @Override
    public void consultarOrdens() {

    }

    private void salvarDadosList(ResultSet resultSet){

        PropertiesVendedor PV = new PropertiesVendedor();
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
            propertiesVendedors.add(PV);
        } catch (SQLException e){
            throw new DBException(e.getMessage());
        }
    }
}
