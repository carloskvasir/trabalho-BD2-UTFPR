CREATE OR REPLACE VIEW vw_listagem_vendas AS
SELECT
    v.ven_codigo,
    v.ven_horario,
    v.ven_valor_total,
    v.tb_funcionario_fun_codigo,
    f.fun_nome,
    f.fun_user,
    p.pro_descricao,
    i.ite_quantidade,
    i.ite_valor_parcial
FROM
    tb_vendas v
        JOIN
    tb_funcionarios f ON v.tb_funcionario_fun_codigo = f.fun_codigo
        JOIN
    tb_itens i ON v.ven_codigo = i.tb_venda_ven_codigo
        JOIN
    tb_produtos p ON i.tb_produtos_pro_codigo = p.pro_codigo
ORDER BY
    v.ven_horario;

CREATE OR REPLACE FUNCTION relatorio_vendas_periodo(
    p_data_inicio DATE,
    p_data_fim DATE
)
RETURNS TABLE (
    ven_codigo BIGINT,
    "Data da Venda" DATE,
    "Valor Total da Venda" DECIMAL(7,2),
    "Codigo do Funcionario" BIGINT,
    "Nome do Funcionario" VARCHAR
) AS $$
BEGIN
RETURN QUERY
SELECT
    v.ven_codigo,
    v.ven_horario,
    v.ven_valor_total,
    f.fun_codigo,
    f.fun_nome
FROM
    vw_listagem_vendas v
        JOIN
    tb_funcionarios f ON v.tb_funcionario_fun_codigo = f.fun_codigo
WHERE
    v.ven_horario BETWEEN p_data_inicio AND p_data_fim
ORDER BY
    v.ven_horario;
END;
$$ LANGUAGE plpgsql;

-- -- Chamada da função
-- SELECT * FROM relatorio_vendas_periodo(
--         '2024-04-01',  -- Data de início
--         '2024-04-30'   -- Data de fim
--               );