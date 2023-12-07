
	-- Cirando o database
    CREATE DATABASE Coffast;
    USE Coffast;
    -- tabela de usuario
    CREATE TABLE user (
 	id_user int AUTO_INCREMENT PRIMARY KEY,
     name_user varchar(45),
    email_user varchar(50),
     password_user varchar(50)
     
 );

select * from user;
-- tabela de produtos
 create table product (
 	id_product int AUTO_INCREMENT PRIMARY KEY,
    name_product varchar(45),
    description_product varchar(100)
);

-- tabela de relacionamento entre produtos e usuarios
CREATE TABLE pedidos_clientes_produtos (
   id_pedido INT AUTO_INCREMENT PRIMARY KEY,
    id_user INT,
    id_product INT,
    amount INT,
    FOREIGN KEY (id_user) REFERENCES user(id_user),
    FOREIGN KEY (id_product) REFERENCES product(id_product)
);


select * from pedidos_clientes_produtos;

select * from user;

-- query qye faz o join para unir o pedido, produto e usuario
SELECT pedidos_clientes_produtos.id_pedido, user.name_user, product.name_product
FROM user
JOIN pedidos_clientes_produtos ON user.id_user = pedidos_clientes_produtos.id_user
JOIN product ON pedidos_clientes_produtos.id_product = product.id_product
ORDER BY id_pedido;