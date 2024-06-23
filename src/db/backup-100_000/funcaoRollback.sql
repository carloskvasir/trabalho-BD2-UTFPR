CREATE OR REPLACE FUNCTION tentar_inserir_com_rollback(
    p_fun_codigo BIGINT,
    p_fun_user VARCHAR,
    p_fun_nome VARCHAR,
    p_fun_cpf VARCHAR,
    p_fun_senha VARCHAR,
    p_fun_funcao VARCHAR
) RETURNS VOID AS $$
BEGIN
    -- Tenta inserir um novo funcionário na tabela
    INSERT INTO tb_funcionarios (
        fun_codigo, fun_user, fun_nome, fun_cpf, fun_senha, fun_funcao
    ) VALUES (
        p_fun_codigo, p_fun_user, p_fun_nome, p_fun_cpf, p_fun_senha, p_fun_funcao
    );

    -- Levanta uma exceção genérica para causar rollback
    RAISE EXCEPTION 'Simulação de rollback após inserção do funcionário %', p_fun_nome;

EXCEPTION
    WHEN OTHERS THEN
        -- Captura a exceção e notifica o rollback
        RAISE NOTICE 'Rollback realizado: A inserção do funcionário % foi revertida.', p_fun_nome;
        -- Relança a exceção para propagar ao Java
        RAISE;
END;
$$ LANGUAGE plpgsql;
