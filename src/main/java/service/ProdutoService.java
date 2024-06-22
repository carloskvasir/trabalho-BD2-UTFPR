package service;

import domain.Produto;
import repository.ProdutoDAO;

import java.util.List;

public class ProdutoService {

    ProdutoDAO produtoDAO = new ProdutoDAO();

    public List<Produto> getAllProdutos() {
        return produtoDAO.getAllProdutos();
    }

    public List<Produto> findByName(String name) {
        return produtoDAO.findByName(name);
    }
}
