package domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ItemVenda {
    @JsonProperty("codigo")
    private long codigo;

    @JsonProperty("vendaCodigo")
    private long vendaCodigo;

    @JsonProperty("produtoCodigo")
    private long produtoCodigo;

    @JsonProperty("quantidade")
    private int quantidade;

    @JsonProperty("valor")
    private double valor;

}
