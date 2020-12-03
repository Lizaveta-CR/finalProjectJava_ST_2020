CREATE DATABASE musicShop_db DEFAULT CHARACTER SET utf8;


CREATE USER shop_user@localhost IDENTIFIED BY 'shop_password';

GRANT SELECT, INSERT, UPDATE, DELETE, CREATE, DROP, ALTER
    ON musicShop_db.*
    TO shop_user@localhost;