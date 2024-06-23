package service;

import DB_Conection.CurrentUser;
import domain.Funcionario;
import repository.FuncionarioDAO;

public class FuncionarioService {

    public static void setFuncionario() {
        Funcionario funcionario = FuncionarioDAO.getFuncionarioByNameAndPassword(CurrentUser.getInstance().getUsername(), CurrentUser.getInstance().getUsername());

        if (funcionario != null) {
            if (funcionario.getUser().equals(CurrentUser.getInstance().getUsername()) || funcionario.getSenha().equals(CurrentUser.getInstance().getUsername())) {
                CurrentUser.getInstance().setId(funcionario.getCodigo());
            }
        }
    }
}
