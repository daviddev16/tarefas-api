<h1 align="center">
  Tarefas API
</h1>

## Tecnologias
 
- Spring Boot
- Spring Web
- Spring Data JPA
- Spring Boot Test
- Docker Compose
- PostgreSQL


## Como Executar

- Clonar repositório git:
```
git clone https://github.com/daviddev16/tarefas-api.git
```

- Executar a aplicação Spring Boot e Containers
  
```
mvn spring-boot:run
```

> Não é necessário usar `docker-compose up` por que a aplicação Spring já faz isso automaticamente na inicialização.


## Acesso
 
- Acessar aplicação em `http://localhost:8080/tarefas-api`.
- Documentação da API `http://localhost:8080/tarefas-api/swagger-ui/index.html`.




## API

- Adicionar um pessoa (post/pessoas)
```
POST localhost:8080/tarefas-api/v1/pessoa
```
- Alterar um pessoa (put/pessoas/{id})
```
PUT localhost:8080/tarefas-api/v1/pessoa/{pessoaId}
```		
- Remover pessoa (delete/pessoas/{id})
```
DELETE localhost:8080/tarefas-api/v1/pessoa/{pessoaId}
```				
- Adicionar um tarefa (post/tarefas)
```
DELETE localhost:8080/tarefas-api/v1/tarefa
```
- Alocar uma pessoa na tarefa que tenha o mesmo departamento (put/tarefas/alocar/{id})
```
PUT localhost:8080/tarefas-api/v1/tarefa/alocar/{tarefaId}
```			
- Finalizar a tarefa (put/tarefas/finalizar/{id})
```
PUT localhost:8080/tarefas-api/v1/tarefa/finalizar/{tarefaId}
```		
- Listar pessoas trazendo nome, departamento, total horas gastas nas tarefas.(get/pessoas)
```
GET localhost:8080/tarefas-api/v1/pessoa
```				
- Buscar pessoas por nome e período, retorna média de horas gastas por tarefa. (get/pessoas/gastos)
```
GET localhost:8080/tarefas-api/v1/pessoa/gastos?dataCriacaoInicial=01-01-2023&dataCriacaoFinal=01-12-2024&nome=David
```		
- Listar 3 tarefas que estejam sem pessoa alocada com os prazos mais antigos. (get/tarefas/pendentes)
```
GET localhost:8080/tarefas-api/v1/tarefa/pendentes
```		   
- Listar departamento e quantidade de pessoas e tarefas (get/departamentos)
```
http://localhost:8080/tarefas-api/v1/tarefa/departamentos
```

[Collection do Insomnia](https://github.com/daviddev16/tarefas-api/blob/master/Insomnia-API-Collection.json)
