package domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class Venda {
    private long codigo;
    private LocalDate horario;
    private double valorTotal;
    private long funcionarioCodigo;

}
