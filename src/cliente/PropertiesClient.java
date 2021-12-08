package cliente;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PropertiesClient {
    protected String CPF, nome, telefone, endereco1, cep, bairro, cidade, estado, pass;
    protected Date date;
    protected int idade;
}
