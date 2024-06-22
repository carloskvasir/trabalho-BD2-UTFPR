INSERT INTO tb_fornecedores (for_codigo, for_descricao)
VALUES (1, 'Fornecedor A');

INSERT INTO tb_funcionarios (fun_codigo, fun_nome, fun_cpf, fun_senha, fun_funcao)
VALUES (1, 'João Silva', '12345678901', 'senha123', 'Vendedor');

INSERT INTO tb_produtos (pro_codigo, pro_descricao, pro_valor, pro_quantidade, tb_fornecedores_for_codigo)
VALUES (1, 'Produto X', 99.99, 10, 1);

INSERT INTO tb_vendas (ven_codigo, ven_horario, ven_valor_total, tb_funcionario_fun_codigo)
VALUES (1, '2024-06-21 10:00:00', 199.98, 1);

INSERT INTO tb_itens (ite_codigo, ite_quantidade, ite_valor_parcial, tb_produtos_pro_codigo, tb_venda_ven_codigo)
VALUES (1, 2, 199.98, 1, 1);
