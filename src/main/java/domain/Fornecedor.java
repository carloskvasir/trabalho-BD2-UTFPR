package domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Fornecedor {
    private long codigo;
    private String descricao;

    @Override
    public String toString() {
        return "Fornecedor{" +
                "codigo=" + codigo +
                ", descricao='" + descricao + '\'' +
                '}';
    }
}
