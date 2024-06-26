# TRABALHO DE BANCO DE DADOS

As credenciais por padrão são:
```bash
POSTGRES_DB: trabalho_banco
POSTGRES_USER: postgres
POSTGRES_PASSWORD: postgres
```

## Nesse projeto utlizamos essas ferramentas
 - java openjdk-17
 - maven 3.9.7
 - postgresql 16

## Para rodar o banco pelo docker-compose

### Para iniciar o banco:
```bash
docker-compose up -d
```

Abra a aplicação com o user `postgres` senha `postgres` e
restaure o backup que esta em [link backup](https://github.com/carloskvasir/trabalho-BD2-UTFPR/blob/main/src/db/trabalho_banco_20240625_231006.backup).  
Após isso as funcionalidades de venda e relatorio estarão ativas para esse usuário.  

### Para parar o banco
```bash
docker-compose down
```
