insert into tb_usuario values (1, 'admin', '$2b$12$PCTGqj/mKfZPDhGZUjs4puYKar4EoQ0luel1XIHtF1XsXsGCsoeG2', 'admin@gmail.com', 'ADMIN');
insert into tb_usuario values (2, 'user1', '$2b$12$KfKEsU5oQ.jXFt9982P3VuFF2e4BfCpHPZFMH9lGFvpgcAWCKzCEi', 'user1@gmail.com', 'USER');
insert into tb_usuario values (3, 'user2', '$2b$12$8ivR.f6zcsNnKQX/u1eHl.YH7Qwevl0T1tAlzyIo/l..2N6gjWqem', 'user2@gmail.com', 'USER');
insert into tb_usuario values (4, 'user3', '$2b$12$nNS4cQi1z0yYswFcfYPh5.n2IxArxllgz5Y.jiZnBZ.DLMfiTtRQe', 'user3@gmail.com', 'USER');
insert into tb_usuario values (5, 'user4', '$2b$12$hLAL6L7O507jyqP5CUUpieTTjsCvN/A93BGUqWnvq9Wv7LQErlGrq', 'user4@gmail.com', 'USER');

INSERT INTO tb_produto
(id, nome, descricao, preco, categoria, qt_estoque, dt_criacao, dt_atualizacao)
VALUES ('c8b3a9e5-6f24-4c9e-a74f-08b0fd25a1d1', 'Teclado Mecânico', 'Teclado mecânico com iluminação RGB', 300, 'periferico', 10, '2025-11-08 20:30:00', NULL);

INSERT INTO tb_produto
(id, nome, descricao, preco, categoria, qt_estoque, dt_criacao, dt_atualizacao)
VALUES ('d2f5b1cc-1b93-4b9e-8b8a-3b6c53d6a1a2', 'Mouse Gamer', 'Mouse óptico gamer 16000 DPI', 200, 'periferico', 15, '2025-11-08 20:31:00', NULL);

INSERT INTO tb_produto
(id, nome, descricao, preco, categoria, qt_estoque, dt_criacao, dt_atualizacao)
VALUES ('a1c9e4f7-13b9-44e4-bba9-04a83b22c6d3', 'Monitor 27"', 'Monitor LED 27 polegadas Full HD', 800, 'eletronico', 8, '2025-11-08 20:32:00', NULL);

INSERT INTO tb_produto
(id, nome, descricao, preco, categoria, qt_estoque, dt_criacao, dt_atualizacao)
VALUES ('f6a2d57b-4dc9-4f2a-94c4-4d5cb2a45c8e', 'Headset Gamer', 'Headset com microfone e som surround 7.1', 400, 'periferico', 12, '2025-11-08 20:33:00', NULL);

INSERT INTO tb_pedido
(id, id_usuario, status, valor_total, dt_criacao, dt_atualizacao)
VALUES (1, 1, 'PENDENTE', 500, '2025-11-09 21:00:00', NULL);

INSERT INTO tb_pedido
(id, id_usuario, status, valor_total, dt_criacao, dt_atualizacao)
VALUES (2, 1, 'PENDENTE', 1000, '2025-11-09 21:10:00', NULL);

INSERT INTO tb_pedido
(id, id_usuario, status, valor_total, dt_criacao, dt_atualizacao)
VALUES (3, 1, 'PENDENTE', 700, '2025-11-09 21:20:00', NULL);

INSERT INTO tb_pedido
(id, id_usuario, status, valor_total, dt_criacao, dt_atualizacao)
VALUES (4, 1, 'PENDENTE', 600, '2025-11-09 21:30:00', NULL);

INSERT INTO tb_pedido
(id, id_usuario, status, valor_total, dt_criacao, dt_atualizacao)
VALUES (5, 1, 'PENDENTE', 1000, '2025-11-09 21:40:00', NULL);

INSERT INTO tb_item_pedido
(id, id_pedido, id_produto, quantidade, subtotal)
VALUES (1, 1, 'd2f5b1cc-1b93-4b9e-8b8a-3b6c53d6a1a2', 1, 200);

INSERT INTO tb_item_pedido
(id, id_pedido, id_produto, quantidade, subtotal)
VALUES (2, 1, 'f6a2d57b-4dc9-4f2a-94c4-4d5cb2a45c8e', 1, 300);

INSERT INTO tb_item_pedido
(id, id_pedido, id_produto, quantidade, subtotal)
VALUES (3, 2, 'a1c9e4f7-13b9-44e4-bba9-04a83b22c6d3', 1, 800);

INSERT INTO tb_item_pedido
(id, id_pedido, id_produto, quantidade, subtotal)
VALUES (4, 2, 'd2f5b1cc-1b93-4b9e-8b8a-3b6c53d6a1a2', 1, 200);

INSERT INTO tb_item_pedido
(id, id_pedido, id_produto, quantidade, subtotal)
VALUES (5, 3, 'f6a2d57b-4dc9-4f2a-94c4-4d5cb2a45c8e', 1, 400);

INSERT INTO tb_item_pedido
(id, id_pedido, id_produto, quantidade, subtotal)
VALUES (6, 3, 'd2f5b1cc-1b93-4b9e-8b8a-3b6c53d6a1a2', 1, 200);

INSERT INTO tb_item_pedido
(id, id_pedido, id_produto, quantidade, subtotal)
VALUES (7, 4, 'a1c9e4f7-13b9-44e4-bba9-04a83b22c6d3', 1, 800);

INSERT INTO tb_item_pedido
(id, id_pedido, id_produto, quantidade, subtotal)
VALUES (8, 4, 'd2f5b1cc-1b93-4b9e-8b8a-3b6c53d6a1a2', 1, 200);

INSERT INTO tb_item_pedido
(id, id_pedido, id_produto, quantidade, subtotal)
VALUES (9, 5, 'a1c9e4f7-13b9-44e4-bba9-04a83b22c6d3', 1, 800);

INSERT INTO tb_item_pedido
(id, id_pedido, id_produto, quantidade, subtotal)
VALUES (10, 5, 'f6a2d57b-4dc9-4f2a-94c4-4d5cb2a45c8e', 1, 400);