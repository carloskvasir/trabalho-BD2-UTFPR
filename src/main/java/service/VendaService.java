package service;

import com.fasterxml.jackson.core.JsonProcessingException;
import domain.ItemVenda;
import domain.Venda;
import dao.VendaDAO;

import java.sql.SQLException;
import java.util.List;

public class VendaService {

    static VendaDAO vendaDAO = new VendaDAO();

    public static void inserirVendaComItens(Venda venda, List<ItemVenda> itens) throws SQLException, JsonProcessingException {
        vendaDAO.inserirVendaComItens(venda, itens);
    }



}
