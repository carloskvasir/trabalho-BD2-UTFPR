DROP TABLE tb_itens;
DROP TABLE tb_vendas;
DROP TABLE tb_funcionarios;
DROP TABLE tb_produtos;
DROP TABLE tb_fornecedores;
DROP TABLE product_groups;

--- functions

DROP FUNCTION IF EXISTS relatorio_vendas_funcionario(BIGINT, DATE, DATE);
