package domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ItemVenda {
    private long codigo;
    private long vendaCodigo;
    private long produtoCodigo;
    private int quantidade;
    private double valor;

}
