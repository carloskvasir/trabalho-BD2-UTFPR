package service;

import domain.ItemVenda;
import domain.Venda;
import repository.VendaDAO;

import java.util.List;

public class VendaService {

    static VendaDAO vendaDAO = new VendaDAO();

    public static Long insert(Venda venda) {
        return vendaDAO.insert(venda);
    }

    public static void inserirVendaComItens(Venda venda, List<ItemVenda> itens) {
        vendaDAO.inserirVendaComItens(venda, itens);
    }

}
