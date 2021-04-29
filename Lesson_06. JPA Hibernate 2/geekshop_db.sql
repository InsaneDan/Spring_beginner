
-- MySQL
SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema geekshop
-- -----------------------------------------------------
# CREATE SCHEMA IF NOT EXISTS geekshop;
USE geekshop;
-- -----------------------------------------------------
-- Table `user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
  id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  PRIMARY KEY (id))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

-- `user` data
INSERT INTO `users` (name) VALUES
('User1'),
('User2'),
('User3');

-- -----------------------------------------------------
-- Table `orders`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `orders`;

CREATE TABLE IF NOT EXISTS `orders` (
  id INT NOT NULL AUTO_INCREMENT,
  user_id INT NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT fk_order_user
    FOREIGN KEY (user_id)
    REFERENCES `users` (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

CREATE INDEX fk_order_user_idx ON `orders` (user_id ASC) VISIBLE;

-- `order` data
INSERT INTO `orders` (user_id) VALUES
(1),
(1),
(1),
(2),
(1),
(3);

-- -----------------------------------------------------
-- Table `products`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `products`;

CREATE TABLE IF NOT EXISTS `products` (
  id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  price DECIMAL(10,2) NOT NULL,
  PRIMARY KEY (id))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

INSERT INTO products (name, price) VALUES
('Product 1', 100),
('Product 2', 200.02),
('Product 3', 33.33),
('Product 4', 444.00),
('Product 5', 5.05);
-- -----------------------------------------------------
-- Table `order_products`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `order_products`;

CREATE TABLE IF NOT EXISTS `order_products` (
  id INT NOT NULL AUTO_INCREMENT,
  product_id INT NOT NULL,
  order_id INT NOT NULL,
  quantity INT NOT NULL,
  price DECIMAL(10,2) NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT fk_order_products_products
    FOREIGN KEY (product_id)
    REFERENCES `products` (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_order_products_order
    FOREIGN KEY (order_id)
    REFERENCES `orders` (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

CREATE INDEX fk_order_products_products_idx ON order_products (product_id ASC) VISIBLE;
CREATE INDEX fk_order_products_order_idx ON order_products (order_id ASC) VISIBLE;

INSERT INTO `order_products` (product_id, order_id, quantity, price) VALUES
(1, 1, 1, 100),
(3, 1, 3, 3),
(4, 1, 5, 44),
(1, 2, 3, 555),
(1, 3, 2, 100),
(4, 3, 6, 200),
(5, 4, 1, 300),
(1, 5, 7, 100),
(5, 5, 5, 150),
(1, 6, 6, 130),
(2, 6, 7, 120),
(4, 6, 8, 110),
(5, 6, 9, 190);

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
