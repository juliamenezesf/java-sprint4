
# Saúde Digital API (Java 21 + HTTP Server + JDBC Oracle)

API simples (sem Spring), usando **Java 21**, **HttpServer** embutido e **JDBC** para gravar na tabela `SUPORTE_SOLICITACAO` do Oracle.  
Atende aos requisitos da sprint e integra com o **Frontend (React)** do formulário *"Preciso de Ajuda"*.

## 🎯 Objetivo da Integração
- **Frontend (React)**: envia `fetch()` para `POST /api/suportesolicitacao` com JSON do formulário.
- **Backend (Java)**: recebe o JSON, **insere** na tabela `SUPORTE_SOLICITACAO` via JDBC, e retorna mensagem de sucesso/erro.
- **Banco de Dados (Oracle)**: persiste os dados e permite relatórios gerenciais (já implementados no seu SQL).

## ✅ Requisitos Atendidos
- Java 21 (sem Spring, **sem Lombok**).
- Endpoints REST:
  - `GET  /health` – health-check
  - `GET  /version` – versão da API
  - `POST /api/suportesolicitacao` – **criar** solicitação
  - `GET  /api/suportesolicitacao` – **listar** todas
  - `GET  /api/suportesolicitacao/{id}` – **detalhar** uma
  - `PUT  /api/suportesolicitacao/{id}` – **atualizar** (nome/telefone/problema/status)
  - `DELETE /api/suportesolicitacao/{id}` – **excluir**
- JDBC + DAO + Service + Model organizados em camadas.
- **Variáveis de ambiente** `DB_USER` e `DB_PASS` no IntelliJ (sem senha no código).
- CORS habilitado para integração com React.
- Pronto para rodar no IntelliJ (arquivo `.run` incluso).

## 📦 Pré-requisitos
- **JDK 21+** instalado.
- Acesso ao Oracle FIAP:
  - HOST: `oracle.fiap.com.br`
  - PORT: `1521`
  - SID:  `ORCL`
  - Usuário e senha: fornecidos por você.
- **Driver JDBC da Oracle**:
  - Opção A (recomendada): adicione o `ojdbc11.jar` na pasta `lib/` e inclua no *Project Structure* → *Modules* → *Dependencies*.
  - Opção B (Maven): você pode criar um `pom.xml` com a dependência `com.oracle.database.jdbc:ojdbc11`, porém este projeto foi mantido **sem Maven** para ficar 1:1 com as aulas.

> Dica: Baixe o `ojdbc11.jar` (versão 23.x) do site oficial da Oracle ou Maven Central e coloque dentro de `lib/`.

## 🔐 Configurar variáveis de ambiente no IntelliJ
1. Abra `Main.java` e clique no ícone de **Run** → **Edit Configurations...**  
2. Em **Environment variables**, adicione:
   ```
   DB_USER=RM559932;DB_PASS=310795
   ```
3. Salve e execute `Main`.

> Use as suas credenciais reais. Elas **não** ficam hardcoded no projeto.

## ▶️ Rodar
- Abra o projeto no IntelliJ.
- Garanta que o `ojdbc11.jar` (ou equivalente) está referenciado no módulo.
- Clique **Run ▶️** em `Main`.  
- A API sobe em: **http://localhost:8080**

## 🧪 Teste rápido (curl ou HTTP client)
Criar:
```bash
curl -X POST http://localhost:8080/api/suportesolicitacao   -H "Content-Type: application/json"   -d '{"nome_contato":"Sueli","telefone_contato":"(11)91333-1010","problema_relato":"Teste via API","status_atendimento":"ABERTO"}'
```

Listar:
```bash
curl http://localhost:8080/api/suportesolicitacao
```

Atualizar:
```bash
curl -X PUT http://localhost:8080/api/suportesolicitacao/1   -H "Content-Type: application/json"   -d '{"status_atendimento":"EM ANDAMENTO"}'
```

Deletar:
```bash
curl -X DELETE http://localhost:8080/api/suportesolicitacao/1
```

## 🔗 Integração Frontend (React)
No seu formulário *"Preciso de Ajuda"*, faça o `fetch` para `http://localhost:8080/api/suportesolicitacao` com o mesmo JSON dos exemplos acima.  
CORS já está liberado.

## 🗂️ Estrutura
```
saude-digital-api/
  .run/
    SaudeDigitalApi.run.xml
  lib/
    (coloque aqui o ojdbc11.jar)
  src/
    main/
      java/br/com/fiap/saude/
        Main.java
        config/DatabaseConfig.java
        dao/SuporteSolicitacaoDAO.java
        http/JsonUtil.java
        http/Router.java
        model/SuporteSolicitacao.java
        service/SuporteSolicitacaoService.java
  README.md
  .gitignore
```

## 📄 Tabela utilizada
`SUPORTE_SOLICITACAO(id_solicitacao, nome_contato, telefone_contato, problema_relato, status_atendimento, dt_criacao)`

## 🛡️ Observações Importantes
- Certifique-se de que a tabela existe (seu script SQL da sprint já cria).
- `dt_criacao` tem `DEFAULT SYSDATE` (o insert não precisa enviar o campo).
- Trate corretamente acentos/UTF-8 no seu terminal e no frontend.

---

**Autor:** Pedro Henrique Costa (RM559932) e Júlia Kauane Menezes (RM565568)  
**Disciplina:** Back-end Java – Sprint 4 – FIAP
