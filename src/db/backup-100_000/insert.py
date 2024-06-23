import psycopg2
import os

# Função para conectar ao banco de dados PostgreSQL
def connect_db():
    try:
        connection = psycopg2.connect(
            host="localhost",       # Endereço do servidor
            database="trabalho_banco",   # Nome do banco de dados
            user="postgres",        # Usuário do banco de dados
            password="postgres"     # Senha do banco de dados
        )
        return connection
    except Exception as error:
        print(f"Erro ao conectar ao banco de dados: {error}")
        return None

# Função para ler e executar SQL dos arquivos
def execute_sql_files(directory):
    connection = connect_db()
    if not connection:
        return

    try:
        cursor = connection.cursor()
        # Ordem específica dos arquivos SQL
        sql_files_order = [
            'tb_funcionarios.sql',
            'product_groups.sql',
            'tb_fornecedores.sql',
            'tb_produtos.sql',
            'tb_vendas.sql',
            'tb_itens.sql'
        ]
        
        for sql_file in sql_files_order:
            file_path = os.path.join(directory, sql_file)
            with open(file_path, 'r') as file:
                sql_statements = file.read()
                print(f"Executando comandos do arquivo: {sql_file}")
                cursor.execute(sql_statements)
                connection.commit()
                print(f"Comandos do arquivo {sql_file} executados com sucesso.")
    except Exception as error:
        print(f"Erro ao executar os comandos SQL: {error}")
        if connection:
            connection.rollback()
    finally:
        if cursor:
            cursor.close()
        if connection:
            connection.close()

# Diretório onde estão os arquivos SQL
sql_directory = 'caminho/para/diretorio/com/arquivos_sql'  # Ajuste este caminho conforme necessário

# Executar a função
execute_sql_files(sql_directory)
