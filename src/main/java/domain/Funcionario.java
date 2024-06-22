package domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Funcionario {
    private long codigo;
    private String nome;
    private String cpf;
    private String senha;
    private String funcao;
    private String user;

    @Override
    public String toString() {
        return "Funcionario{" +
                "codigo=" + codigo +
                ", nome='" + nome + '\'' +
                ", cpf='" + cpf + '\'' +
                ", senha='" + senha + '\'' +
                ", funcao='" + funcao + '\'' +
                '}';
    }
}
