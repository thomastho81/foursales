insert into tb_usuario values
(1, 'thomas', 'thomax', 'thom.bp', 'USER');

insert into tb_usuario values
(2, 'user2', 'user2', 'thom.bp', 'USER');

insert into tb_usuario values
(3, 'user3', 'user3', 'thom.bp', 'USER');

insert into tb_usuario values
(4, 'user4', 'user4', 'thom.bp', 'USER');

insert into tb_usuario values
(5, 'user5', 'user5', 'thom.bp', 'USER');

INSERT INTO tb_produto
(id, nome, descricao, preco, categoria, qt_estoque, dt_criacao, dt_atualizacao)
VALUES('390392c7-8215-4583-a83e-e5a927e69479', 'produto2', 'monitor', 50.00, 'eletronico', 10, '2025-11-08 20:34:02', NULL);

INSERT INTO tb_produto
(id, nome, descricao, preco, categoria, qt_estoque, dt_criacao, dt_atualizacao)
VALUES('57cdf0fc-59f5-4fe0-ad5f-943ab0522932', 'produto1', 'cadeira', 150.00, 'casa', 10, '2025-11-08 20:33:43', NULL);

INSERT INTO tb_pedido
(id, id_usuario, status, valor_total, dt_criacao, dt_atualizacao)
VALUES(1, 1, 'PENDENTE', 200.00, '2025-11-09 21:57:48', NULL);

INSERT INTO tb_pedido
(id, id_usuario, status, valor_total, dt_criacao, dt_atualizacao)
VALUES(2, 2, 'PENDENTE', 250.00, '2025-11-09 21:57:55', NULL);

INSERT INTO tb_pedido
(id, id_usuario, status, valor_total, dt_criacao, dt_atualizacao)
VALUES(3, 3, 'PENDENTE', 400.00, '2025-11-09 21:58:01', NULL);

INSERT INTO tb_pedido
(id, id_usuario, status, valor_total, dt_criacao, dt_atualizacao)
VALUES(4, 4, 'PENDENTE', 500.00, '2025-11-09 21:58:13', NULL);

INSERT INTO tb_pedido
(id, id_usuario, status, valor_total, dt_criacao, dt_atualizacao)
VALUES(5, 5, 'PENDENTE', 300.00, '2025-11-09 21:58:19', NULL);

INSERT INTO tb_item_pedido
(id, id_pedido, id_produto, quantidade, subtotal)
VALUES(1, 1, '390392c7-8215-4583-a83e-e5a927e69479', 1, 50.00);

INSERT INTO tb_item_pedido
(id, id_pedido, id_produto, quantidade, subtotal)
VALUES(2, 1, '57cdf0fc-59f5-4fe0-ad5f-943ab0522932', 1, 150.00);

INSERT INTO tb_item_pedido
(id, id_pedido, id_produto, quantidade, subtotal)
VALUES(3, 2, '390392c7-8215-4583-a83e-e5a927e69479', 2, 100.00);

INSERT INTO tb_item_pedido
(id, id_pedido, id_produto, quantidade, subtotal)
VALUES(4, 2, '57cdf0fc-59f5-4fe0-ad5f-943ab0522932', 1, 150.00);

INSERT INTO tb_item_pedido
(id, id_pedido, id_produto, quantidade, subtotal)
VALUES(5, 3, '390392c7-8215-4583-a83e-e5a927e69479', 2, 100.00);

INSERT INTO tb_item_pedido
(id, id_pedido, id_produto, quantidade, subtotal)
VALUES(6, 3, '57cdf0fc-59f5-4fe0-ad5f-943ab0522932', 2, 300.00);

INSERT INTO tb_item_pedido
(id, id_pedido, id_produto, quantidade, subtotal)
VALUES(7, 4, '390392c7-8215-4583-a83e-e5a927e69479', 1, 50.00);

INSERT INTO tb_item_pedido
(id, id_pedido, id_produto, quantidade, subtotal)
VALUES(8, 4, '57cdf0fc-59f5-4fe0-ad5f-943ab0522932', 3, 450.00);

INSERT INTO tb_item_pedido
(id, id_pedido, id_produto, quantidade, subtotal)
VALUES(9, 5, '390392c7-8215-4583-a83e-e5a927e69479', 3, 150.00);

INSERT INTO tb_item_pedido
(id, id_pedido, id_produto, quantidade, subtotal)
VALUES(10, 5, '57cdf0fc-59f5-4fe0-ad5f-943ab0522932', 1, 150.00);