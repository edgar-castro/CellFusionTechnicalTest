-- Tables
CREATE DATABASE IF NOT EXISTS inventory_system_test;

USE inventory_system_test;

CREATE TABLE IF NOT EXISTS stock_status (
    id INT PRIMARY KEY AUTO_INCREMENT,
    meaning VARCHAR(255) NOT NULL,
    color VARCHAR(7) NOT NULL
 );


CREATE TABLE IF NOT EXISTS warehouse_plant (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name varchar(128) NOT NULL,
    min_products INT NOT NULL,
    max_products INT NOT NULL,
    stock_status INT NOT NULL,
    FOREIGN KEY (stock_status) REFERENCES stock_status(id)
 );

 CREATE TABLE IF NOT EXISTS product (
     id int PRIMARY KEY AUTO_INCREMENT,
     name VARCHAR(255) NOT NULL,
     total INT NOT NULL,
     sold INT NOT NULL
);

CREATE TABLE IF NOT EXISTS warehouse_plant_product (
    id INT PRIMARY KEY AUTO_INCREMENT,
    id_warehouse_plant INT NOT NULL,
    id_product INT NOT NULL,
    FOREIGN KEY (id_warehouse_plant) REFERENCES warehouse_plant(id) ON DELETE CASCADE,
    FOREIGN KEY (id_product) REFERENCES product(id) ON DELETE CASCADE
);

-- Triggers
--      calculate stock status on warehouse_plant_product insert
DELIMITER $
CREATE TRIGGER update_stock_status AFTER INSERT ON warehouse_plant_product
FOR EACH ROW BEGIN
    SET @ID_P := (
        SELECT warehouse_plant_product.id_product
        FROM warehouse_plant_product
        ORDER BY warehouse_plant_product.id DESC
        LIMIT 1
    );
    SET @ID_W := (
        SELECT warehouse_plant_product.id_warehouse_plant
        FROM warehouse_plant_product
        ORDER BY warehouse_plant_product.id DESC
        LIMIT 1
    );

    SET @REM := (
        SELECT product.total - product.sold
        FROM product
        WHERE product.id = @ID_P
    );
    SET @MIN := (
        SELECT warehouse_plant.min_products
        FROM warehouse_plant
        WHERE warehouse_plant.id = @ID_W
    );

    SET @MAX := (
        SELECT warehouse_plant.max_products
        FROM warehouse_plant
        WHERE warehouse_plant.id = @ID_W
    );

    IF @REM < @MIN THEN
        UPDATE warehouse_plant SET warehouse_plant.stock_status = 3 WHERE warehouse_plant.id = @ID_W;
    ELSEIF @REM > @MAX THEN
        UPDATE warehouse_plant SET warehouse_plant.stock_status = 4 WHERE warehouse_plant.id = @ID_W;
    ELSEIF @REM = @MAX THEN
        UPDATE warehouse_plant SET warehouse_plant.stock_status = 5 WHERE warehouse_plant.id = @ID_W;
    ELSE
        UPDATE warehouse_plant SET warehouse_plant.stock_status = 2 WHERE warehouse_plant.id = @ID_W;
    END IF;
END$
DELIMITER ;

--      Calculate stock status on warehouse_plant_product update

DELIMITER $
CREATE TRIGGER update_stock_status_update AFTER UPDATE ON warehouse_plant_product
FOR EACH ROW BEGIN
    SET @ID_P := new.id_product;
    SET @ID_W := new.id_warehouse_plant;

    SET @REM := (
        SELECT product.total - product.sold
        FROM product
        WHERE product.id = @ID_P
    );
    SET @MIN := (
        SELECT warehouse_plant.min_products
        FROM warehouse_plant
        WHERE warehouse_plant.id = @ID_W
    );
    SET @MAX := (
        SELECT warehouse_plant.max_products
        FROM warehouse_plant
        WHERE warehouse_plant.id = @ID_W
    );

    IF @REM < @MIN THEN
        UPDATE warehouse_plant SET warehouse_plant.stock_status = 3 WHERE warehouse_plant.id = @ID_W;
    ELSEIF @REM > @MAX THEN
        UPDATE warehouse_plant SET warehouse_plant.stock_status = 4 WHERE warehouse_plant.id = @ID_W;
    ELSEIF @REM = @MAX THEN
        UPDATE warehouse_plant SET warehouse_plant.stock_status = 5 WHERE warehouse_plant.id = @ID_W;
    ELSE
        UPDATE warehouse_plant SET warehouse_plant.stock_status = 2 WHERE warehouse_plant.id = @ID_W;
    END IF;
END$
DELIMITER ;

-- Calculate stock status on product update

DELIMITER $
CREATE TRIGGER update_stock_status_product_update AFTER UPDATE ON product
FOR EACH ROW BEGIN
    SET @ID_P := new.id;
    SET @ID_W := (SELECT id_warehouse_plant FROM warehouse_plant_product WHERE id_product = @ID_P);

    SET @REM := (
        SELECT product.total - product.sold
        FROM product
        WHERE product.id = @ID_P
    );
    SET @MIN := (
        SELECT warehouse_plant.min_products
        FROM warehouse_plant
        WHERE warehouse_plant.id = @ID_W
    );
    SET @MAX := (
        SELECT warehouse_plant.max_products
        FROM warehouse_plant
        WHERE warehouse_plant.id = @ID_W
    );

    IF @REM < @MIN THEN
        UPDATE warehouse_plant SET warehouse_plant.stock_status = 3 WHERE warehouse_plant.id = @ID_W;
    ELSEIF @REM > @MAX THEN
        UPDATE warehouse_plant SET warehouse_plant.stock_status = 4 WHERE warehouse_plant.id = @ID_W;
    ELSEIF @REM = @MAX THEN
        UPDATE warehouse_plant SET warehouse_plant.stock_status = 5 WHERE warehouse_plant.id = @ID_W;
    ELSE
        UPDATE warehouse_plant SET warehouse_plant.stock_status = 2 WHERE warehouse_plant.id = @ID_W;
    END IF;
END$
DELIMITER ;

-- Default data
INSERT INTO stock_status (meaning, color)
VALUES
("Empty", "#FFFFFF"),
("Normal", "#FFFFFF"),
("Not enough products", "#FF0000"),
("More than necessary", "#FFFF00"),
("Full capacity", "#00FF00");

INSERT INTO product (name, total, sold)
VALUES ("Sprite", 20, 3), ("Coca-Cola", 50, 2), ("Pepsi", 30, 22);

INSERT INTO warehouse_plant (name, min_products, max_products, stock_status)
VALUES ("Plant 1", 5, 10, 1), ("Plant 2", 10, 40, 1), ("Plant 3", 1, 5, 1);

