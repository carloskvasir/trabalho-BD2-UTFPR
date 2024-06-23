CREATE OR REPLACE FUNCTION inserir_venda_com_itens(
    p_horario TIMESTAMP,
    p_valor_total NUMERIC,
    p_funcionario_codigo BIGINT,
    p_itens JSONB
) RETURNS BIGINT AS $$
DECLARE
    v_id_venda BIGINT;
BEGIN
    -- Ajustar a sequência
    PERFORM setval(pg_get_serial_sequence('tb_vendas', 'ven_codigo'), COALESCE(MAX(ven_codigo) + 1, 1), false) FROM tb_vendas;
    PERFORM setval(pg_get_serial_sequence('tb_itens', 'ite_codigo'), COALESCE(MAX(ite_codigo) + 1, 1), false) FROM tb_itens;

    -- Inserir a venda
    INSERT INTO tb_vendas (ven_horario, ven_valor_total, tb_funcionario_fun_codigo)
    VALUES (p_horario, p_valor_total, p_funcionario_codigo)
    RETURNING ven_codigo INTO v_id_venda;

    -- Inserir os itens da venda
    INSERT INTO tb_itens (ite_quantidade, ite_valor_parcial, tb_produtos_pro_codigo, tb_venda_ven_codigo)
    SELECT
        (item->>'quantidade')::INT,
        (item->>'valor')::NUMERIC,
        (item->>'produtoCodigo')::BIGINT,
        v_id_venda
    FROM jsonb_array_elements(p_itens) AS item;

    -- Atualizar o estoque dos produtos
    UPDATE tb_produtos
    SET pro_quantidade = pro_quantidade - (item->>'quantidade')::INT
    FROM jsonb_array_elements(p_itens) AS item
    WHERE tb_produtos.pro_codigo = (item->>'produtoCodigo')::BIGINT;

    RETURN v_id_venda;

EXCEPTION
    WHEN OTHERS THEN
        -- Rollback a transação no caso de erro
        RAISE;
END;
$$ LANGUAGE plpgsql;
