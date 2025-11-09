DROP TABLE IF EXISTS tb_item_pedido;
DROP TABLE IF EXISTS tb_pedido;
DROP TABLE IF EXISTS tb_produto;
DROP TABLE IF EXISTS tb_usuario;

-- Tabela de Produtos
CREATE TABLE IF NOT EXISTS tb_produto (
    id VARCHAR(36) NOT NULL COMMENT 'Identificador único (UUID) do produto',
    nome VARCHAR(255) COMMENT 'Nome do produto',
    descricao VARCHAR(255) COMMENT 'Descrição detalhada do produto',
    preco DECIMAL(10,2) COMMENT 'Preço unitário do produto',
    categoria VARCHAR(255) COMMENT 'Categoria do produto (ex: Eletrônicos, Roupas, etc.)',
    qt_estoque INT COMMENT 'Quantidade disponível em estoque',
    dt_criacao DATETIME COMMENT 'Data de criação do registro',
    dt_atualizacao DATETIME COMMENT 'Data da última atualização do registro',
    PRIMARY KEY (id)
) COMMENT='Tabela que armazena os produtos disponíveis no sistema.';

-- Tabela de Usuários
CREATE TABLE IF NOT EXISTS tb_usuario (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT 'Identificador sequencial único do usuário',
    username VARCHAR(255) COMMENT 'Nome de usuário utilizado para login',
    senha VARCHAR(255) COMMENT 'Senha criptografada para autenticação',
    email VARCHAR(255) COMMENT 'Endereço de e-mail do usuário',
    role VARCHAR(50) COMMENT 'Papel do usuário no sistema (ex: ADMIN, CLIENTE)',
    PRIMARY KEY (id)
) COMMENT='Tabela que armazena as credenciais e perfis de acesso dos usuários.';

-- Tabela de Pedidos
CREATE TABLE IF NOT EXISTS tb_pedido (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT 'Identificador sequencial único do pedido',
    id_usuario BIGINT NOT NULL COMMENT 'Chave estrangeira referenciando o usuário que fez o pedido',
    status VARCHAR(50) COMMENT 'Status atual do pedido (ex: ABERTO, FINALIZADO, CANCELADO)',
    valor_total DECIMAL(10,2) COMMENT 'Valor total do pedido (somatório dos itens)',
    dt_criacao DATETIME COMMENT 'Data de criação do pedido',
    dt_atualizacao DATETIME COMMENT 'Data de atualização do pedido',
    PRIMARY KEY (id),
    CONSTRAINT fk_tb_pedido_01 FOREIGN KEY (id_usuario)
        REFERENCES tb_usuario (id)
) COMMENT='Tabela que armazena os pedidos realizados pelos usuários.';

-- Tabela de Itens do Pedido
CREATE TABLE IF NOT EXISTS tb_item_pedido (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT 'Identificador sequencial único do item do pedido',
    id_pedido BIGINT NOT NULL COMMENT 'Chave estrangeira para o pedido relacionado',
    id_produto CHAR(36) NOT NULL COMMENT 'Chave estrangeira para o produto relacionado (UUID)',
    quantidade INT COMMENT 'Quantidade do produto neste pedido',
    subtotal DECIMAL(10,2) COMMENT 'Valor subtotal deste item (quantidade * preço do produto)',
    PRIMARY KEY (id),
    CONSTRAINT fk_tb_item_pedido_01 FOREIGN KEY (id_pedido)
        REFERENCES tb_pedido (id),
    CONSTRAINT fk_tb_item_pedido_02 FOREIGN KEY (id_produto)
        REFERENCES tb_produto (id),
    UNIQUE (id_pedido, id_produto)
) COMMENT='Tabela que armazena os itens pertencentes a cada pedido.';