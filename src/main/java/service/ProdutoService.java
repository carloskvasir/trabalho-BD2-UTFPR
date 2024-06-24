package service;

import domain.Produto;
import dao.ProdutoDAO;

import java.util.List;

public class ProdutoService {

    ProdutoDAO produtoDAO = new ProdutoDAO();

    public List<Produto> getAllProdutos() {
        return produtoDAO.getAllProdutos();
    }

    public List<Produto> findByName(String name) {
        if (name == null || name.isEmpty()) {
            return getAllProdutos();
        }
        return produtoDAO.findByName(name);
    }
}
