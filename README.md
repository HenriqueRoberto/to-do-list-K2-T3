# ZG-Hero TODO List

Aplicação de **Lista de Tarefas (TODO List)** com **interface web (Kanban)** e **back-end em Java (CLI)**.

---

## Front-end Web

Interface visual desenvolvida em **HTML, CSS e JavaScript puro**, com modelo **Kanban** de três colunas: **TODO, DOING e DONE**.

### Funcionalidades
- Criar, visualizar, editar e excluir tarefas
- Organização automática por prioridade
- Separação por **workspaces**
- Filtros por **status**, **categoria** e **prioridade**
- **Alertas por e-mail** via integração com Mailtrap (SMTP)

### Alertas de E-mail
A aplicação envia e-mails automaticamente nas seguintes situações:

| Situação | Quando dispara |
|---|---|
| Tarefa criada | Ao criar uma nova tarefa |
| Tarefa concluída | Ao salvar uma tarefa com status DONE |
| Prazo vencendo | Ao abrir o app, se alguma tarefa vence amanhã |
| Tarefa atrasada | Ao abrir o app, se alguma tarefa está com prazo vencido |

### Estrutura dos arquivos JS

```
assets/js/
├── email.js       → configuração e funções de envio de e-mail (Mailtrap)
├── workspace.js   → estado e lógica dos workspaces
├── task.js        → CRUD de tarefas, montagem de cards e renderização
├── ui.js          → dropdowns e filtros
└── modal.js       → modais de criar e visualizar/editar tarefa
```

### Tecnologias
- HTML5
- CSS3
- JavaScript (Vanilla JS)
- [Mailtrap](https://mailtrap.io) — serviço SMTP para envio de e-mails

---

## Back-end Java (CLI)

Aplicação executada via **terminal**, com arquitetura **MVC + SOLID**.

### Funcionalidades
- Criar, listar, atualizar e deletar tarefas
- Filtros por status, categoria, prioridade, data exata e data limite

### Regras de Negócio
- ID autogerado
- Nome e prioridade obrigatórios
- Prioridade entre 1 e 5
- Status padrão: TODO
- Rebalanceamento automático por prioridade
- Data de término não pode estar no passado
- Atualização parcial (altera apenas os campos informados)

### Estrutura dos arquivos Java

```
src/
├── app/
│   └── Main.java             → inicialização e injeção de dependências
├── model/
│   ├── Task.java             → entidade de dados
│   └── TaskStatus.java       → enum de status
├── service/
│   └── TaskService.java      → regras de negócio e armazenamento
├── controller/
│   └── TaskController.java   → orquestrador entre service e view
└── view/
    ├── MenuView.java         → fluxos de menu e exibição
    └── InputView.java        → leitura de input do usuário
```

### Tecnologias
- Java
- Programação Orientada a Objetos (POO)
- Arquitetura MVC
- Princípios SOLID

### Como executar
1. Abra o projeto na IDE (IntelliJ IDEA recomendado)
2. Localize a classe `src/app/Main.java`
3. Execute
4. O menu aparecerá no terminal

---

## Observações
- O front-end funciona em memória no navegador, sem persistência de dados
- Futuramente será integrado ao back-end Java via API REST
