INSERT INTO product_groups (id, name, description) VALUES
   (1, 'Eletrônicos', 'Dispositivos eletrônicos e gadgets'),
   (2, 'Roupas', 'Vestuário para todas as idades'),
   (3, 'Alimentos', 'Produtos alimentícios e bebidas'),
   (4, 'Livros', 'Diversos tipos de livros e publicações'),
   (5, 'Móveis', 'Móveis para casa e escritório'),
   (6, 'Brinquedos', 'Brinquedos para crianças de todas as idades'),
   (7, 'Ferramentas', 'Ferramentas e equipamentos'),
   (8, 'Esportes', 'Artigos esportivos e equipamentos'),
   (9, 'Beleza', 'Produtos de beleza e higiene'),
   (10, 'Automotivo', 'Peças e acessórios automotivos'),
   (11, 'Papelaria', 'Produtos de papelaria e escritório'),
   (12, 'Jardinagem', 'Ferramentas e suprimentos para jardinagem'),
   (13, 'Saúde', 'Produtos de saúde e bem-estar'),
   (14, 'Informática', 'Computadores e acessórios'),
   (15, 'Casa', 'Artigos de decoração e utilidades domésticas'),
   (16, 'Calçados', 'Diversos tipos de calçados'),
   (17, 'Bebês', 'Produtos para bebês'),
   (18, 'Animais', 'Produtos para animais de estimação'),
   (19, 'Joias', 'Joias e bijuterias'),
   (20, 'Eletrodomésticos', 'Eletrodomésticos para casa');

----

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

----

INSERT INTO tb_produtos (pro_codigo, pro_descricao, pro_valor, pro_quantidade, product_group, tb_fornecedores_for_codigo) VALUES
    (1, 'Smartphone X', 599.99, 50, 1, 1),
    (2, 'Calça Jeans', 49.99, 100, 2, 2),
    (3, 'Café Premium', 7.99, 200, 3, 3),
    (4, 'Romance Clássico', 19.99, 150, 4, 4),
    (5, 'Cadeira Escritório', 89.99, 40, 5, 5),
    (6, 'Carrinho de Brinquedo', 9.99, 300, 6, 6),
    (7, 'Chave de Fenda', 3.99, 250, 7, 7),
    (8, 'Bola de Futebol', 24.99, 80, 8, 8),
    (9, 'Shampoo XYZ', 12.99, 120, 9, 9),
    (10, 'Filtro de Óleo', 5.99, 60, 10, 10),
    (11, 'Caderno Universitário', 2.99, 500, 11, 11),
    (12, 'Tesoura de Jardim', 14.99, 30, 12, 12),
    (13, 'Termômetro Digital', 19.99, 70, 13, 13),
    (14, 'Mouse Óptico', 29.99, 90, 14, 14),
    (15, 'Vaso Decorativo', 22.99, 55, 15, 15),
    (16, 'Tênis Esportivo', 59.99, 150, 16, 16),
    (17, 'Fralda Descartável', 13.99, 200, 17, 17),
    (18, 'Ração para Cães', 25.99, 180, 18, 18),
    (19, 'Anel de Prata', 49.99, 70, 19, 19),
    (20, 'Geladeira Frost Free', 799.99, 20, 20, 20);

----

INSERT INTO tb_funcionarios (fun_codigo, fun_user, fun_nome, fun_cpf, fun_senha, fun_funcao) VALUES
    (1, 'postgres', 'João Silva', '12345678901', 'postgres', 'Master'),
    (2, '1', 'Maria Oliveira', '12345678902', '1', 'Master'),
    (3, 'p.almeida', 'Pedro Almeida', '12345678903', 'senha789', 'Caixa'),
    (4, 'a.souza', 'Ana Souza', '12345678904', 'senha012', 'Vendedor'),
    (5, 'c.pereira', 'Carlos Pereira', '12345678905', 'senha345', 'Estoquista'),
    (6, 'b.gomes', 'Beatriz Gomes', '12345678906', 'senha678', 'Vendedor'),
    (7, 'r.santos', 'Rafael Santos', '12345678907', 'senha901', 'Gerente'),
    (8, 'f.lima', 'Fernanda Lima', '12345678908', 'senha234', 'Caixa'),
    (9, 'l.costa', 'Lucas Costa', '12345678909', 'senha567', 'Vendedor'),
    (10, 'j.torres', 'Juliana Torres', '12345678910', 'senha890', 'Vendedor'),
    (11, 'g.rocha', 'Gabriel Rocha', '12345678911', 'senha111', 'Estoquista'),
    (12, 'l.carvalho', 'Letícia Carvalho', '12345678912', 'senha222', 'Vendedor'),
    (13, 'r.martins', 'Rodrigo Martins', '12345678913', 'senha333', 'Caixa'),
    (14, 'l.freitas', 'Larissa Freitas', '12345678914', 'senha444', 'Vendedor'),
    (15, 'v.barros', 'Vinícius Barros', '12345678915', 'senha555', 'Gerente'),
    (16, 'n.ribeiro', 'Natália Ribeiro', '12345678916', 'senha666', 'Vendedor'),
    (17, 't.fernandes', 'Thiago Fernandes', '12345678917', 'senha777', 'Caixa'),
    (18, 'd.mota', 'Daniela Mota', '12345678918', 'senha888', 'Estoquista'),
    (19, 'f.costa', 'Felipe Costa', '12345678919', 'senha999', 'Vendedor'),
    (20, 'i.melo', 'Isabela Melo', '12345678920', 'senha000', 'Gerente');


----

INSERT INTO tb_vendas (ven_codigo, ven_horario, ven_valor_total, tb_funcionario_fun_codigo) VALUES
    (1, '2024-06-21 10:00:00', 1199.96, 1),
    (2, '2024-06-21 10:10:00', 249.95, 1),
    (3, '2024-06-21 10:20:00', 439.96, 1),
    (4, '2024-06-21 10:30:00', 199.90, 1),
    (5, '2024-06-21 10:40:00', 179.91, 5),
    (6, '2024-06-21 10:50:00', 299.98, 1),
    (7, '2024-06-21 11:00:00', 299.97, 7),
    (8, '2024-06-21 11:10:00', 129.94, 8),
    (9, '2024-06-21 11:20:00', 169.96, 9),
    (10, '2024-06-21 11:30:00', 114.95, 10),
    (11, '2024-06-21 11:40:00', 134.92, 11),
    (12, '2024-06-21 11:50:00', 284.91, 12),
    (13, '2024-06-21 12:00:00', 189.93, 13),
    (14, '2024-06-21 12:10:00', 194.94, 14),
    (15, '2024-06-21 12:20:00', 324.96, 15),
    (16, '2024-06-21 12:30:00', 479.92, 16),
    (17, '2024-06-21 12:40:00', 569.94, 17),
    (18, '2024-06-21 12:50:00', 284.97, 18),
    (19, '2024-06-21 13:00:00', 399.93, 19),
    (20, '2024-06-21 13:10:00', 799.88, 20);

----

INSERT INTO tb_itens (ite_codigo, ite_quantidade, ite_valor_parcial, tb_produtos_pro_codigo, tb_venda_ven_codigo) VALUES
    (1, 2, 1199.96, 1, 1),
    (2, 5, 249.95, 2, 2),
    (3, 55, 439.96, 3, 3),
    (4, 10, 199.90, 4, 4),
    (5, 2, 179.91, 5, 5),
    (6, 30, 299.98, 6, 6),
    (7, 4, 299.97, 7, 7),
    (8, 8, 129.94, 8, 8),
    (9, 13, 169.96, 9, 9),
    (10, 3, 114.95, 10, 10),
    (11, 3, 134.92, 11, 11),
    (12, 19, 284.91, 12, 12),
    (13, 9, 189.93, 13, 13),
    (14, 10, 194.94, 14, 14),
    (15, 7, 324.96, 15, 15),
    (16, 16, 479.92, 16, 16),
    (17, 5, 569.94, 17, 17),
    (18, 11, 284.97, 18, 18),
    (19, 8, 399.93, 19, 19),
    (20, 1, 799.88, 20, 20);

----

select * from tb_itens;