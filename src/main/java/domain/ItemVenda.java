package domain;

public class ItemVenda {
    private long codigo;
    private long vendaCodigo;
    private long produtoCodigo;
    private int quantidade;
    private double valor;

    // Construtores, getters e setters
    public ItemVenda(long codigo, long vendaCodigo, long produtoCodigo, int quantidade, double valor) {
        this.codigo = codigo;
        this.vendaCodigo = vendaCodigo;
        this.produtoCodigo = produtoCodigo;
        this.quantidade = quantidade;
        this.valor = valor;
    }

    public long getCodigo() {
        return codigo;
    }

    public void setCodigo(long codigo) {
        this.codigo = codigo;
    }

    public long getVendaCodigo() {
        return vendaCodigo;
    }

    public void setVendaCodigo(long vendaCodigo) {
        this.vendaCodigo = vendaCodigo;
    }

    public long getProdutoCodigo() {
        return produtoCodigo;
    }

    public void setProdutoCodigo(long produtoCodigo) {
        this.produtoCodigo = produtoCodigo;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
}
