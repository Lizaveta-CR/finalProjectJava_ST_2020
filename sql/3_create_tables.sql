USE musicShop_db;
DROP table users;

CREATE TABLE users
(
    login    VARCHAR(255) NOT NULL,
    password CHAR(32)     NOT NULL,
    /*
     * 0 - администратор (Role.ADMINISTRATOR)
     * 1 - менеджер (Role.MANAGER)
     */
    role     TINYINT      NOT NULL CHECK (role IN (0, 1)),
    CONSTRAINT UC_User UNIQUE (login)

) ENGINE = INNODB
  DEFAULT CHARACTER SET utf8;
CREATE INDEX idx_login ON users (login);
ALTER TABLE users
    ADD name VARCHAR(255) DEFAULT '';
ALTER TABLE users
    ADD surname VARCHAR(255) DEFAULT '';

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
ALTER TABLE buyers
    ADD address_id INTEGER NOT NULL;
ALTER TABLE buyers
    DROP address_id;

ALTER TABLE buyers
    ADD name VARCHAR(255) DEFAULT '';
ALTER TABLE buyers
    ADD surname VARCHAR(255) DEFAULT '';


CREATE TABLE addresses
(
    buyer_id         INTEGER      NOT NULL,
    country          VARCHAR(30)  NOT NULL,
    city             VARCHAR(30)  NOT NULL,
    street           VARCHAR(30)  NOT NULL,
    apartment_number INTEGER(100) NOT NULL,
    house_number     INTEGER(100),
    CONSTRAINT PK_Address PRIMARY KEY (buyer_id),
    CONSTRAINT FK_Address_Buyer FOREIGN KEY (buyer_id)
        REFERENCES buyers (buyer_id) ON DELETE CASCADE
) ENGINE = INNODB
  DEFAULT CHARACTER
      SET utf8;
ALTER TABLE addresses
    DROP country;
ALTER TABLE addresses
    ADD country_id INTEGER NOT NULL;

ALTER TABLE addresses
    ADD CONSTRAINT FK_Country FOREIGN KEY (country_id) REFERENCES countries (country_id);

CREATE TABLE products
(
    product_id INTEGER      NOT NULL AUTO_INCREMENT,
    type       VARCHAR(255) NOT NULL,
    CONSTRAINT PK_Product PRIMARY KEY (product_id)
) ENGINE = INNODB
  DEFAULT CHARACTER
      SET utf8;

ALTER TABLE products
    CHANGE description description VARCHAR(2500);
ALTER TABLE products
    ADD name_id INTEGER NOT NULL;

ALTER TABLE
    products
    ADD CONSTRAINT FK_Products_Name FOREIGN KEY (name_id) REFERENCES instruments (name_id)
        ON UPDATE CASCADE
        ON DELETE CASCADE;
CREATE TABLE producers
(
#     TODO:
    producer_id INTEGER       NOT NULL AUTO_INCREMENT,
    name    VARCHAR(255)  NOT NULL,
    country DECIMAL(4, 2) NOT NULL,
    CONSTRAINT PK_Producer PRIMARY KEY (name, country)
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

CREATE TABLE order_items
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
ALTER TABLE order_items
    ADD amount INTEGER NOT NULL CHECK ( amount > 0 );

CREATE TABLE producer_items
(
    producer_id INTEGER NOT NULL,
    product_id  INTEGER NOT NULL,
    CONSTRAINT PK_ProducerItem PRIMARY KEY (producer_id, product_id),
    CONSTRAINT FK_ProducerItem_Producer
        FOREIGN KEY FK_Order (producer_id) REFERENCES producers (producer_id)
            ON UPDATE CASCADE
            ON DELETE RESTRICT,
    CONSTRAINT FK_ProducerItem_Product
        FOREIGN KEY FK_Product (product_id) REFERENCES products (product_id)
            ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = INNODB
  DEFAULT CHARACTER SET utf8;
ALTER TABLE producer_items
    ADD price DECIMAL(10, 2);

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
    ADD CONSTRAINT CK_Constrain_Value CHECK ( value <= 10 );
# ALTER TABLE product_rates DROP CONSTRAINT CK_Constrain_Value;
# ALTER TABLE product_rates
#     CHANGE value mark TINYINT NOT NULL ;
DROP TABLE countries;
CREATE TABLE countries
(
    country_id   INTEGER        NOT NULL AUTO_INCREMENT,
    country_code CHAR(2)        NOT NULL,
    country_name varchar(40)    NOT NULL,
    region_id    decimal(10, 0) NOT NULL,
    CONSTRAINT UNx_Countries UNIQUE (country_code, region_id),
    CONSTRAINT PK_Countries PRIMARY KEY (country_id)
) ENGINE = INNODB
  DEFAULT CHARACTER SET utf8;

CREATE TABLE instruments
(
    name_id INTEGER NOT NULL AUTO_INCREMENT,
    name    CHAR(3) NOT NULL UNIQUE,
    CONSTRAINT PK_Instrument PRIMARY KEY (name_id)
) ENGINE = INNODB
  DEFAULT CHARACTER SET utf8;
ALTER TABLE instruments
    CHANGE name name VARCHAR(20);