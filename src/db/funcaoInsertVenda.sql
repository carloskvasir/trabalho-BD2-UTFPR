CREATE OR REPLACE FUNCTION inserir_venda_com_itens(
    p_horario TIMESTAMP,
    p_valor_total NUMERIC,
    p_funcionario_codigo BIGINT,
    p_itens JSONB
) RETURNS BIGINT AS $$
DECLARE
    v_id_venda BIGINT;
BEGIN
    -- Iniciar uma transação
    BEGIN
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

        -- Se tudo ocorrer bem, retorne o ID da venda
        RETURN v_id_venda;

    EXCEPTION
        WHEN OTHERS THEN
            -- Em caso de erro, fazer rollback
            RAISE NOTICE 'Erro ao inserir venda e itens. Executando rollback.';
            PERFORM pg_sleep(0);  -- Apenas para garantir que o rollback ocorra.
            ROLLBACK;
            RETURN NULL;
    END;
END;
$$ LANGUAGE plpgsql;
