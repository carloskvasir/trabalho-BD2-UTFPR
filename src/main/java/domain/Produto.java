package domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Produto {
    private long codigo;
    private String descricao;
    private double valor;
    private int quantidade;
    private long fornecedorCodigo;

    @Override
    public String toString() {
        return "Produto{" +
                "codigo=" + codigo +
                ", descricao='" + descricao + '\'' +
                ", valor=" + valor +
                ", quantidade=" + quantidade +
                ", fornecedorCodigo=" + fornecedorCodigo +
                '}';
    }
}
