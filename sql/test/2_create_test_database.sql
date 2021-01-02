CREATE DATABASE musicShopTest_db DEFAULT CHARACTER SET utf8;

CREATE USER 'testShop_user'@'localhost' IDENTIFIED BY 'testShop_password';
GRANT ALL PRIVILEGES
    ON musicShopTest_db.*
    TO testShop_user@localhost ;

CREATE USER 'testShop_user'@'%' IDENTIFIED BY 'testShop_password';
GRANT ALL PRIVILEGES ON musicShopTest_db.*
    TO testShop_user@'%';