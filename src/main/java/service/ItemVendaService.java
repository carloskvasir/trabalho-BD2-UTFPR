package service;

import domain.ItemVenda;
import dao.ItemVendaDAO;

import java.util.List;

public class ItemVendaService {

    static ItemVendaDAO itemVendaDAO = new ItemVendaDAO();

    public static void insert(List<ItemVenda> itens)  {
        itemVendaDAO.insert(itens);
    }
}
