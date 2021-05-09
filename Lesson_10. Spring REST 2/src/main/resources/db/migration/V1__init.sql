CREATE TABLE IF NOT EXISTS users (
                                     id bigserial PRIMARY KEY,
                                     name      VARCHAR2 NOT NULL);

DROP TABLE IF EXISTS orders;
CREATE TABLE IF NOT EXISTS orders (
                                      id bigserial PRIMARY KEY,
                                      user_id    INT NOT NULL,
                                      FOREIGN KEY (user_id) REFERENCES users (id));

DROP TABLE IF EXISTS products;
CREATE TABLE IF NOT EXISTS products (
                                        id bigserial PRIMARY KEY,
                                        title      VARCHAR2 NOT NULL,
                                        price     DECIMAL(10,2) NOT NULL);

DROP TABLE IF EXISTS order_products;
CREATE TABLE IF NOT EXISTS order_products (
                                              id bigserial PRIMARY KEY,
                                              order_id   INT NOT NULL,
                                              product_id INT NOT NULL,
                                              quantity  INT NOT NULL,
                                              acquire_price DECIMAL(10,2) NOT NULL,
    CONSTRAINT uk_order_products UNIQUE (order_id, product_id),
    CONSTRAINT fk_order_id FOREIGN KEY (order_id) REFERENCES orders(id),
    CONSTRAINT fk_product_id FOREIGN KEY (product_id) REFERENCES products(id));


INSERT INTO users (name)
VALUES ('User1'),
       ('User2'),
       ('User3');

INSERT INTO orders (user_id)
VALUES (1),
       (1),
       (1),
       (2),
       (1),
       (3);

INSERT INTO products (title, price)
VALUES ('Product 01', 10),
       ('Product 02', 20),
       ('Product 03', 30),
       ('Product 04', 40),
       ('Product 05', 50),
       ('Product 06', 60),
       ('Product 07', 70),
       ('Product 08', 80),
       ('Product 09', 90),
       ('Product 10', 100),
       ('Product 11', 110),
       ('Product 12', 120),
       ('Product 13', 130),
       ('Product 14', 140),
       ('Product 15', 150),
       ('Product 16', 160),
       ('Product 17', 170),
       ('Product 18', 180),
       ('Product 19', 190),
       ('Product 20', 200),
       ('Product 21', 210);

INSERT INTO order_products (product_id, order_id, quantity, acquire_price)
VALUES (1, 1, 1, 100.55),
       (3, 1, 3, 3.55),
       (4, 1, 5, 44.5),
       (1, 2, 3, 555.5),
       (1, 3, 2, 100.5),
       (4, 3, 6, 200.5),
       (5, 4, 1, 300.5),
       (1, 5, 7, 100.5),
       (5, 5, 5, 150.5),
       (1, 6, 6, 130.5),
       (2, 6, 7, 120.5),
       (4, 6, 8, 110.5),
       (5, 6, 9, 190.5);