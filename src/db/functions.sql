CREATE OR REPLACE FUNCTION relatorio_vendas_funcionario(
    p_fun_codigo BIGINT,     -- Código do funcionário
    p_data_inicio DATE,      -- Data de início do período
    p_data_fim DATE          -- Data de fim do período
)
RETURNS TABLE (
    "Codigo do Funcionário" BIGINT,
    "Nome do Funcionário" VARCHAR,
    "Quantidade de Vendas" BIGINT,
    "Valor Total das Vendas" DECIMAL
) AS $$
BEGIN
RETURN QUERY
SELECT
    f.fun_codigo AS "Codigo do Funcionário",
    f.fun_nome AS "Nome do Funcionário",
    COUNT(v.ven_codigo) AS "Quantidade de Vendas",
    SUM(v.ven_valor_total) AS "Valor Total das Vendas"
FROM
    tb_vendas v
        JOIN
    tb_funcionarios f ON v.tb_funcionario_fun_codigo = f.fun_codigo
WHERE
    f.fun_codigo = p_fun_codigo
  AND v.ven_horario BETWEEN p_data_inicio::timestamp
  AND (p_data_fim::timestamp + INTERVAL '1 day' - INTERVAL '1 second')
GROUP BY
    f.fun_codigo;
END;
$$ LANGUAGE plpgsql;

----
SELECT * FROM relatorio_vendas_funcionario(
1,                             -- Código do funcionário
'2024-06-21',                  -- Data de início
'2024-06-22'                   -- Data de fim
);
----
