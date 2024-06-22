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
-----------------------------
INSERT INTO tb_fornecedores (for_codigo, for_descricao) VALUES
(1, 'Fornecedor A'),
(2, 'Fornecedor B'),
(3, 'Fornecedor C'),
(4, 'Fornecedor D'),
(5, 'Fornecedor E'),
(6, 'Fornecedor F'),
(7, 'Fornecedor G'),
(8, 'Fornecedor H'),
(9, 'Fornecedor I'),
(10, 'Fornecedor J'),
(11, 'Fornecedor K'),
(12, 'Fornecedor L'),
(13, 'Fornecedor M'),
(14, 'Fornecedor N'),
(15, 'Fornecedor O'),
(16, 'Fornecedor P'),
(17, 'Fornecedor Q'),
(18, 'Fornecedor R'),
(19, 'Fornecedor S'),
(20, 'Fornecedor T');

INSERT INTO tb_produtos (pro_codigo, pro_descricao, pro_valor, pro_quantidade, tb_fornecedores_for_codigo) VALUES
(1, 'Produto A', 49.99, 100, 1),
(2, 'Produto B', 79.99, 50, 2),
(3, 'Produto C', 19.99, 200, 3),
(4, 'Produto D', 299.99, 30, 4),
(5, 'Produto E', 159.99, 75, 5),
(6, 'Produto F', 39.99, 150, 6),
(7, 'Produto G', 89.99, 40, 7),
(8, 'Produto H', 59.99, 60, 8),
(9, 'Produto I', 109.99, 25, 9),
(10, 'Produto J', 24.99, 80, 10),
(11, 'Produto K', 99.99, 120, 11),
(12, 'Produto L', 49.99, 90, 12),
(13, 'Produto M', 129.99, 35, 13),
(14, 'Produto N', 69.99, 70, 14),
(15, 'Produto O', 199.99, 20, 15),
(16, 'Produto P', 249.99, 45, 16),
(17, 'Produto Q', 79.99, 100, 17),
(18, 'Produto R', 159.99, 55, 18),
(19, 'Produto S', 219.99, 65, 19),
(20, 'Produto T', 39.99, 130, 20);

INSERT INTO tb_funcionarios (fun_codigo, fun_nome, fun_cpf, fun_senha, fun_funcao) VALUES
(1, 'João Silva', '12345678901', 'senha123', 'Vendedor'),
(2, 'Maria Oliveira', '12345678902', 'senha456', 'Gerente'),
(3, 'Pedro Almeida', '12345678903', 'senha789', 'Caixa'),
(4, 'Ana Souza', '12345678904', 'senha012', 'Vendedor'),
(5, 'Carlos Pereira', '12345678905', 'senha345', 'Estoquista'),
(6, 'Beatriz Gomes', '12345678906', 'senha678', 'Vendedor'),
(7, 'Rafael Santos', '12345678907', 'senha901', 'Gerente'),
(8, 'Fernanda Lima', '12345678908', 'senha234', 'Caixa'),
(9, 'Lucas Costa', '12345678909', 'senha567', 'Vendedor'),
(10, 'Juliana Torres', '12345678910', 'senha890', 'Vendedor'),
(11, 'Gabriel Rocha', '12345678911', 'senha111', 'Estoquista'),
(12, 'Letícia Carvalho', '12345678912', 'senha222', 'Vendedor'),
(13, 'Rodrigo Martins', '12345678913', 'senha333', 'Caixa'),
(14, 'Larissa Freitas', '12345678914', 'senha444', 'Vendedor'),
(15, 'Vinícius Barros', '12345678915', 'senha555', 'Gerente'),
(16, 'Natália Ribeiro', '12345678916', 'senha666', 'Vendedor'),
(17, 'Thiago Fernandes', '12345678917', 'senha777', 'Caixa'),
(18, 'Daniela Mota', '12345678918', 'senha888', 'Estoquista'),
(19, 'Felipe Costa', '12345678919', 'senha999', 'Vendedor'),
(20, 'Isabela Melo', '12345678920', 'senha000', 'Gerente');

INSERT INTO tb_vendas (ven_codigo, ven_horario, ven_valor_total, tb_funcionario_fun_codigo) VALUES
(1, '2024-06-21 10:00:00', 199.98, 1),
(2, '2024-06-21 10:10:00', 398.97, 2),
(3, '2024-06-21 10:20:00', 149.97, 3),
(4, '2024-06-21 10:30:00', 299.99, 4),
(5, '2024-06-21 10:40:00', 99.99, 5),
(6, '2024-06-21 10:50:00', 399.98, 6),
(7, '2024-06-21 11:00:00', 249.99, 7),
(8, '2024-06-21 11:10:00', 299.97, 8),
(9, '2024-06-21 11:20:00', 149.98, 9),
(10, '2024-06-21 11:30:00', 199.99, 10),
(11, '2024-06-21 11:40:00', 299.98, 11),
(12, '2024-06-21 11:50:00', 99.99, 12),
(13, '2024-06-21 12:00:00', 399.97, 13),
(14, '2024-06-21 12:10:00', 249.98, 14),
(15, '2024-06-21 12:20:00', 149.99, 15),
(16, '2024-06-21 12:30:00', 199.97, 16),
(17, '2024-06-21 12:40:00', 399.99, 17),
(18, '2024-06-21 12:50:00', 299.99, 18),
(19, '2024-06-21 13:00:00', 99.98, 19),
(20, '2024-06-21 13:10:00', 249.99, 20);

INSERT INTO tb_itens (ite_codigo, ite_quantidade, ite_valor_parcial, tb_produtos_pro_codigo, tb_venda_ven_codigo) VALUES
(1, 2, 99.98, 1, 1),
(2, 3, 239.97, 2, 2),
(3, 5, 99.95, 3, 3),
(4, 1, 299.99, 4, 4),
(5, 2, 199.98, 1, 5),
(6, 4, 159.96, 6, 6),
(7, 3, 149.97, 7, 7),
(8, 2, 89.98, 8, 8),
(9, 1, 109.99, 9, 9),
(10, 4, 99.96, 10, 10),
(11, 2, 199.98, 11, 11),
(12, 1, 49.99, 12, 12),
(13, 3, 389.97, 13, 13),
(14, 5, 199.95, 14, 14),
(15, 2, 99.98, 15, 15),
(16, 1, 249.99, 16, 16),
(17, 3, 239.97, 17, 17),
(18, 2, 319.98, 18, 18),
(19, 1, 219.99, 19, 19),
(20, 4, 159.96, 20, 20);

