CREATE TABLE product_groups (
                                id INT PRIMARY KEY,
                                name VARCHAR(30) NOT NULL,
                                description VARCHAR(255)
);

create table tb_fornecedores(
                                for_codigo BIGINT PRIMARY KEY,
                                for_descricao VARCHAR(45)
);

CREATE TABLE tb_produtos(
                            pro_codigo BIGINT PRIMARY KEY,
                            pro_descricao VARCHAR(255),
                            pro_valor DECIMAL NOT NULL,
                            pro_quantidade INT NOT NULL,
                            product_group INT NOT NULL,
                            tb_fornecedores_for_codigo BIGINT NOT NULL,
                            CONSTRAINT fk_product_group
                                FOREIGN KEY (product_group)
                                    REFERENCES product_groups(id),
                            CONSTRAINT fk_fornecedores
                                FOREIGN KEY (tb_fornecedores_for_codigo)
                                    REFERENCES tb_fornecedores(for_codigo)
);

CREATE TABLE tb_funcionarios (
                                 fun_codigo BIGINT PRIMARY KEY,
                                 fun_user VARCHAR(65) NOT NULL,
                                 fun_nome VARCHAR(65) NOT NULL,
                                 fun_cpf VARCHAR(11) NOT NULL,
                                 fun_senha VARCHAR(50) NOT NULL,
                                 fun_funcao VARCHAR(50) NOT NULL
);

CREATE TABLE tb_vendas (
                           ven_codigo BIGSERIAL PRIMARY KEY,
                           ven_horario DATE NOT NULL,
                           ven_valor_total DECIMAL(7,2) NOT NULL,
                           tb_funcionario_fun_codigo BIGINT NOT NULL,
                           CONSTRAINT fk_funcionarios
                               FOREIGN KEY (tb_funcionario_fun_codigo)
                                   REFERENCES tb_funcionarios(fun_codigo)
);

CREATE TABLE tb_itens (
                          ite_codigo BIGSERIAL PRIMARY KEY,
                          ite_quantidade INT NOT NULL,
                          ite_valor_parcial DECIMAL,
                          tb_produtos_pro_codigo BIGINT,
                          tb_venda_ven_codigo BIGINT,
                          CONSTRAINT fk_produtos
                              FOREIGN KEY (tb_produtos_pro_codigo)
                                  REFERENCES tb_produtos(pro_codigo),
                          CONSTRAINT fk_vendas
                              FOREIGN KEY (tb_venda_ven_codigo)
                                  REFERENCES tb_vendas(ven_codigo)
);
