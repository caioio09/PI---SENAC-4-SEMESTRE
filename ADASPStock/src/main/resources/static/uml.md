1. Diagrama de Classes
Um diagrama de classes simples para um sistema de gerenciamento de estoque pode incluir as seguintes classes:

Produto

id: int
nome: String
quantidade: int
preco: float
descricao: String
categoria: Categoria
Categoria

id: int
nome: String
Fornecedor

id: int
nome: String
contato: String
EntradaEstoque

id: int
produto: Produto
quantidade: int
dataEntrada: Date
fornecedor: Fornecedor
SaidaEstoque

id: int
produto: Produto
quantidade: int
dataSaida: Date
destino: String
2. Casos de Uso
Alguns casos de uso que podem ser considerados:

Adicionar Produto
Remover Produto
Atualizar Produto
Registrar Entrada de Estoque
Registrar Saída de Estoque
Consultar Estoque
Gerar Relatório de Estoque
3. Scripts para Banco de Dados
Aqui está um exemplo básico de scripts SQL para criar as tabelas:

-----------------------------------------------------------------------------------------------------------

OBS: para efetuar a entrada de estoque os produtos devem ser cadastrados no sistema com quantidade 0, a partir do cadastro é possível fazer a entrada de estoque e posteriormente saída.

sql
Copiar código
CREATE TABLE Categoria (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(255) NOT NULL
);

CREATE TABLE Fornecedor (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(255) NOT NULL,
    contato VARCHAR(255)
);

CREATE TABLE Produto (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(255) NOT NULL,
    quantidade INT DEFAULT 0,
    preco DECIMAL(10, 2),
    descricao TEXT,
    categoria_id INT,
    FOREIGN KEY (categoria_id) REFERENCES Categoria(id)
);

CREATE TABLE EntradaEstoque (
    id INT PRIMARY KEY AUTO_INCREMENT,
    produto_id INT,
    quantidade INT,
    dataEntrada DATETIME,
    fornecedor_id INT,
    FOREIGN KEY (produto_id) REFERENCES Produto(id),
    FOREIGN KEY (fornecedor_id) REFERENCES Fornecedor(id)
);

CREATE TABLE SaidaEstoque (
    id INT PRIMARY KEY AUTO_INCREMENT,
    produto_id INT,
    quantidade INT,
    dataSaida DATETIME,
    destino VARCHAR(255),
    FOREIGN KEY (produto_id) REFERENCES Produto(id)
);
4. Design Patterns
Alguns design patterns que poderiam ser aplicados nesse sistema incluem:

Singleton: Para gerenciar a conexão com o banco de dados, garantindo que haja apenas uma instância.
Factory Method: Para criar instâncias de produtos, entradas e saídas de estoque, facilitando a criação de objetos com diferentes características.
Observer: Para notificar diferentes partes do sistema sobre mudanças de estado, como alterações de estoque.
Repository: Para encapsular a lógica de acesso a dados e fornecer uma interface para a manipulação de dados.

--------------------------------------------------------------------------------------------------

+----------------+
|    Categoria    |
+----------------+
| - id: int      |
| - nome: String |
+----------------+
          |
          | 1
          |
          | *
+----------------+
|     Produto     |
+----------------+
| - id: int      |
| - nome: String |
| - quantidade: int |
| - preco: float |
| - descricao: String |
| - categoria_id: int |
+----------------+
          |
          | 1
          |
          | *
+----------------------+
|   EntradaEstoque     | -------------------------------------------------------> fornecedor 
+----------------------+
| - id: int           |
| - produto_id: int   |
| - quantidade: int    |
| - dataEntrada: Date  |
| - fornecedor_id: int |
+----------------------+
          |
          | 1
          |
          | *
+--------------------+
|    SaidaEstoque    |
+--------------------+
| - id: int          |
| - produto_id: int  |
| - quantidade: int   |
| - dataSaida: Date   |
| - destino: String   |
+--------------------+


+------------------+
|    Fornecedor     |
+------------------+
| - id: int        |
| - nome: String   |
| - contato: String |


https://dbdiagram.io/d

Table Categoria {
    id int [pk, increment]
    nome varchar(255) [not null]
}

Table Fornecedor {
    id int [pk, increment]
    nome varchar(255) [not null]
    contato varchar(255)
}

Table Produto {
    id int [pk, increment]
    nome varchar(255) [not null]
    quantidade int [default: 0]
    preco decimal(10, 2)
    descricao text
    categoria_id int [ref: > Categoria.id]
}

Table EntradaEstoque {
    id int [pk, increment]
    produto_id int [ref: > Produto.id]
    quantidade int
    dataEntrada datetime
    fornecedor_id int [ref: > Fornecedor.id]
}

Table SaidaEstoque {
    id int [pk, increment]
    produto_id int [ref: > Produto.id]
    quantidade int
    dataSaida datetime
    destino varchar(255)
}


