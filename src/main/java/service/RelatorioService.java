package service;

import dao.RelatorioDAO;
import view.Relatorio;

import java.time.LocalDate;
import java.util.List;

public class RelatorioService {

    static RelatorioDAO relatorioDAO = new RelatorioDAO();

    public static List<String> getRelatorioVendasPeriodo(LocalDate dataInicio, LocalDate dataFim) {
        return relatorioDAO.getRelatorioVendasPeriodo(dataInicio, dataFim);
    }
}
