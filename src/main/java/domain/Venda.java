package domain;

import java.time.LocalDateTime;
import java.util.List;

public class Venda {
    private long codigo;
    private LocalDateTime horario;
    private double valorTotal;
    private long funcionarioCodigo;
    private List<ItemVenda> itens;

    // Construtor
    public Venda(long codigo, LocalDateTime horario, double valorTotal, long funcionarioCodigo, List<ItemVenda> itens) {
        this.codigo = codigo;
        this.horario = horario;
        this.valorTotal = valorTotal;
        this.funcionarioCodigo = funcionarioCodigo;
        this.itens = itens;
    }

    // Getters e setters
    public long getCodigo() {
        return codigo;
    }

    public void setCodigo(long codigo) {
        this.codigo = codigo;
    }

    public LocalDateTime getHorario() {
        return horario;
    }

    public void setHorario(LocalDateTime horario) {
        this.horario = horario;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public long getFuncionarioCodigo() {
        return funcionarioCodigo;
    }

    public void setFuncionarioCodigo(long funcionarioCodigo) {
        this.funcionarioCodigo = funcionarioCodigo;
    }

    public List<ItemVenda> getItens() {
        return itens;
    }

    public void setItens(List<ItemVenda> itens) {
        this.itens = itens;
    }
}
