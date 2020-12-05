USE musicShop_db;

CREATE TABLE users
(
    user_id  INTEGER      NOT NULL AUTO_INCREMENT,
    login    VARCHAR(255) NOT NULL UNIQUE,
    password CHAR(32)     NOT NULL,
    /*
     * 0 - администратор (Role.ADMINISTRATOR)
     * 1 - менеджер (Role.MANAGER)
     */
    `role`   TINYINT      NOT NULL CHECK (role IN (0, 1)),
    CONSTRAINT PK_User PRIMARY KEY (user_id),
    CONSTRAINT UC_User UNIQUE (login)

) ENGINE = INNODB
  DEFAULT CHARACTER SET utf8;

CREATE TABLE buyers
(
    buyer_id  INTEGER      NOT NULL AUTO_INCREMENT,
    email     VARCHAR(255) NOT NULL UNIQUE,
    password  CHAR(32)     NOT NULL,
    #     TODO:think about type
    telephone BIGINT(13)   NOT NULL UNIQUE,
    balance   DECIMAL(4, 2),
    CONSTRAINT PK_Buyer PRIMARY KEY (buyer_id),
    CONSTRAINT UC_Buyer UNIQUE (email)
) ENGINE = INNODB
  DEFAULT CHARACTER SET utf8;

ALTER TABLE buyers
    CHANGE balance balance DECIMAL(10, 2);
ALTER TABLE buyers
    ADD bonus DECIMAL(10, 2) DEFAULT 0 NOT NULL;

CREATE TABLE addresses
(
    address_id       INTEGER      NOT NULL AUTO_INCREMENT,
    buyer_id         INTEGER      NOT NULL,
    country          VARCHAR(30)  NOT NULL,
    city             VARCHAR(30)  NOT NULL,
    street           VARCHAR(30)  NOT NULL,
    apartment_number INTEGER(100) NOT NULL,
    house_number     INTEGER(100),
    CONSTRAINT PK_Buyer PRIMARY KEY (address_id),
    CONSTRAINT FK_Address_Buyer
        FOREIGN KEY (buyer_id) REFERENCES buyers (buyer_id)
            ON UPDATE CASCADE
            ON DELETE RESTRICT
) ENGINE = INNODB
  DEFAULT CHARACTER
      SET utf8;

CREATE TABLE products
(
    product_id INTEGER      NOT NULL AUTO_INCREMENT,
    type       VARCHAR(255) NOT NULL,
    price      DECIMAL      NOT NULL,
    CONSTRAINT PK_Product PRIMARY KEY (product_id)
) ENGINE = INNODB
  DEFAULT CHARACTER
      SET utf8;

ALTER TABLE products
    CHANGE price price DECIMAL(10, 2);

CREATE TABLE producers
(
#     TODO:
#     producer_id INTEGER       NOT NULL AUTO_INCREMENT,
    name       VARCHAR(255)  NOT NULL,
    country    DECIMAL(4, 2) NOT NULL,
    product_id INTEGER,
    CONSTRAINT PK_Producer PRIMARY KEY (name, country),
    CONSTRAINT FK_Producer_Product
        FOREIGN KEY (product_id) REFERENCES products (product_id)
            ON UPDATE CASCADE
            ON DELETE RESTRICT
) ENGINE = INNODB
  DEFAULT CHARACTER
      SET utf8;
ALTER TABLE producers
    CHANGE country country CHAR(2);
ALTER TABLE producers
    MODIFY name VARCHAR(255) NOT NULL;
ALTER TABLE producers
    MODIFY country CHAR(2) NOT NULL;
ALTER TABLE producers
    DROP PRIMARY KEY;
alter table producers
    add CONSTRAINT UC_Producers UNIQUE (name, country, product_id);
ALTER TABLE producers
    ADD producer_id INT PRIMARY KEY AUTO_INCREMENT NOT NULL;

CREATE table orders
(
    order_id INTEGER       NOT NULL AUTO_INCREMENT,
    date     TIMESTAMP(6)  NOT NULL,
    buyer_id INTEGER,
    price    DECIMAL(4, 2) NOT NULL,
    CONSTRAINT PK_Order PRIMARY KEY (order_id),
    CONSTRAINT UC_Order UNIQUE (order_id, buyer_id),
    CONSTRAINT FK_Order_Buyer FOREIGN KEY (buyer_id) REFERENCES buyers (buyer_id)
)
    ENGINE = INNODB
    DEFAULT CHARACTER
        SET utf8;

ALTER TABLE orders
    CHANGE price price DECIMAL(10, 2);
CREATE TABLE order_item
(
    order_id   INTEGER NOT NULL,
    product_id INTEGER NOT NULL,
    CONSTRAINT PK_OrderItem PRIMARY KEY (order_id, product_id),
    CONSTRAINT FK_OrderItem_Order
        FOREIGN KEY FK_Order (order_id) REFERENCES orders (order_id)
            ON UPDATE CASCADE
            ON DELETE RESTRICT,
    CONSTRAINT FK_OrderItem_Product
        FOREIGN KEY FK_Product (product_id) REFERENCES products (product_id)
            ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = INNODB
  DEFAULT CHARACTER SET utf8;

CREATE TABLE product_rates
(
    rate_id    INTEGER AUTO_INCREMENT NOT NULL,
    value      TINYINT                NOT NULL,
    product_id INTEGER                NOT NULL,
    buyer_id   INTEGER                NOT NULL,
    CONSTRAINT PK_ProductRates PRIMARY KEY (rate_id),
    CONSTRAINT FK_ProductRates_Product
        FOREIGN KEY (product_id) references products (product_id),
    CONSTRAINT FK_ProductRates_Buyer
        FOREIGN KEY (buyer_id) references buyers (buyer_id)
) ENGINE = INNODB
  DEFAULT CHARACTER SET utf8;

ALTER TABLE product_rates
    ADD CONSTRAINT CK_Constrain_Value CHECK ( value <= 10 )