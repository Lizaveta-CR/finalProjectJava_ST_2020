USE musicShop_db;

CREATE TABLE users
(
    user_id  INTEGER      NOT NULL AUTO_INCREMENT,
    login    VARCHAR(255) NOT NULL,
    password CHAR(32)     NOT NULL,
    /*
     * 0 - администратор (Role.ADMINISTRATOR)
     * 1 - менеджер (Role.MANAGER)
     * 2 - покупатель (Role.BUYER)
     */
    role     TINYINT      NOT NULL CHECK (role IN (0, 1, 2)),
    CONSTRAINT UC_User UNIQUE (login),
    CONSTRAINT PK_User PRIMARY KEY (user_id)
);
CREATE INDEX idx_login ON users (login);

CREATE TABLE buyers
(
    buyer_id  INTEGER                        NOT NULL,
    email     VARCHAR(255)                   NOT NULL,
    name      VARCHAR(255)   DEFAULT 'buyer' NOT NULL,
    surname   VARCHAR(255)   DEFAULT ''      NOT NULL,
    telephone BIGINT(15)                     NOT NULL UNIQUE,
    balance   DECIMAL(10, 2),
    bonus     DECIMAL(10, 2) DEFAULT 0       NOT NULL,
#     for blocking
    enabled   BOOLEAN        DEFAULT true    NOT NULL,
    CONSTRAINT PK_Buyer PRIMARY KEY (buyer_id),
    CONSTRAINT FK_Buyer FOREIGN KEY (buyer_id) REFERENCES users (user_id)
        ON UPDATE CASCADE
        ON DELETE RESTRICT,
    CONSTRAINT UC_User UNIQUE (email)
);

CREATE TABLE addresses
(
    buyer_id         INTEGER      NOT NULL,
    country_id       INTEGER      NOT NULL,
    city             VARCHAR(30)  NOT NULL,
    street           VARCHAR(30)  NOT NULL,
    apartment_number INTEGER(100) NOT NULL,
    house_number     INTEGER(100),
    CONSTRAINT PK_Address PRIMARY KEY (buyer_id),
    CONSTRAINT FK_Address_Buyer FOREIGN KEY (buyer_id)
        REFERENCES buyers (buyer_id)
        ON UPDATE CASCADE,
    CONSTRAINT FK_Address_Country FOREIGN KEY (country_id) REFERENCES countries (country_id)
        ON UPDATE CASCADE
);

CREATE TABLE products
(
    product_id  INTEGER NOT NULL AUTO_INCREMENT,
    name_id     INTEGER NOT NULL,
    description VARCHAR(1000) DEFAULT '',
    img         VARCHAR(2083),
    CONSTRAINT PK_Product PRIMARY KEY (product_id),
    CONSTRAINT FK_Products_Name FOREIGN KEY (name_id) REFERENCES instruments (name_id)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
);

CREATE TABLE producers
(
    producer_id INTEGER      NOT NULL AUTO_INCREMENT,
    name        VARCHAR(255) NOT NULL,
    country_id  INTEGER      NOT NULL,
    CONSTRAINT PK_Producer PRIMARY KEY (producer_id),
    CONSTRAINT UNX_Producer UNIQUE (name, country_id),
    CONSTRAINT FK_Producer_Country FOREIGN KEY (country_id) REFERENCES countries (country_id)
        ON UPDATE CASCADE
) ENGINE = INNODB
  DEFAULT CHARACTER
      SET utf8;
CREATE INDEX idx_name ON producers (name);

CREATE table orders
(
    order_id INTEGER        NOT NULL AUTO_INCREMENT,
    buyer_id INTEGER,
#     Or orders_items?
    date     TIMESTAMP(6)   NOT NULL,
    price    DECIMAL(10, 2) NOT NULL,
    CONSTRAINT PK_Order PRIMARY KEY (order_id),
    CONSTRAINT UC_Order UNIQUE (order_id, buyer_id),
    CONSTRAINT FK_Order_Buyer FOREIGN KEY (buyer_id) REFERENCES buyers (buyer_id)
);

CREATE TABLE order_items
(
    order_id   INTEGER        NOT NULL,
    product_id INTEGER        NOT NULL,
    price      DECIMAL(10, 2) NOT NULL,
    amount     INTEGER        NOT NULL CHECK ( amount > 0 ),
    CONSTRAINT PK_OrderItem PRIMARY KEY (order_id, product_id),
    CONSTRAINT FK_OrderItem_Order
        FOREIGN KEY FK_Order (order_id) REFERENCES orders (order_id)
            ON UPDATE CASCADE ON DELETE RESTRICT,
    CONSTRAINT FK_OrderItem_Product
        FOREIGN KEY FK_Product (product_id) REFERENCES products (product_id)
            ON DELETE RESTRICT ON UPDATE CASCADE
);

CREATE TABLE producer_items
(
    producer_id INTEGER        NOT NULL,
    product_id  INTEGER        NOT NULL,
    price       DECIMAL(10, 2) NOT NULL,
    CONSTRAINT PK_ProducerItem PRIMARY KEY (producer_id, product_id),
    CONSTRAINT FK_ProducerItem_Producer
        FOREIGN KEY FK_Order (producer_id) REFERENCES producers (producer_id)
            ON UPDATE CASCADE
            ON DELETE CASCADE,
    CONSTRAINT FK_ProducerItem_Product
        FOREIGN KEY FK_Product (product_id) REFERENCES products (product_id)
            ON DELETE CASCADE
            ON UPDATE CASCADE
);

CREATE TABLE product_rates
(
    rate_id    INTEGER AUTO_INCREMENT NOT NULL,
    mark       TINYINT                NOT NULL,
    product_id INTEGER                NOT NULL,
    buyer_id   INTEGER                NOT NULL,
    CONSTRAINT PK_ProductRates PRIMARY KEY (rate_id),
    CONSTRAINT FK_ProductRates_Product
        FOREIGN KEY (product_id) references products (product_id)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
    CONSTRAINT FK_ProductRates_Buyer
        FOREIGN KEY (buyer_id) references buyers (buyer_id)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
    CONSTRAINT CK_Constrain_Mark CHECK ( mark <= 10 )
);

CREATE TABLE countries
(
    country_id   INTEGER NOT NULL AUTO_INCREMENT,
    country_code CHAR(2) NOT NULL,
    CONSTRAINT UNx_Countries UNIQUE (country_code),
    CONSTRAINT PK_Countries PRIMARY KEY (country_id)
);

CREATE TABLE instruments
(
    name_id INTEGER     NOT NULL AUTO_INCREMENT,
    name    VARCHAR(20) NOT NULL UNIQUE,
    CONSTRAINT PK_Instrument PRIMARY KEY (name_id)
);