USE musicShop_db;

INSERT INTO users(user_id, login, password, role)
VALUES (1, 'admin1', 'e00cf25ad42683b3df678c61f42c6bda', 0),
       (2, 'manager1', 'c240642ddef994358c96da82c0361a58', 1);

INSERT INTO buyers(buyer_id, email, password, telephone, balance)
VALUES (1, 'elizTs@gmail.com', '4b8ed8ec7f3dfb86fc092485f8f7fb63', 375445684811, 100.10),
       (2, 'ksuSokol@mail.ru', '53f115c78173b704702ded23e945861f', 375297385012, 119.99),
       (3, 'kateBest@mail.ru', 'f1b3d4c742ecb604ac0fb2ae39884677', 375334177490, 2000);

INSERT INTO addresses(address_id, buyer_id, country, city, street, apartment_number, house_number)
VALUES (1, 1, 'BY', 'Minsk', 'Pobeda', 12, 12),
       (2, 2, 'BY', 'Grodno', 'Lenina', 7, 98);

INSERT INTO products(product_id, type, price)
VALUES (1, 'Guitar', 1000.87),
       (2, 'Violin', 1124);

INSERT INTO producers(name, country, product_id, producer_id)
VALUES ('Takamine', 'JP', 1, 1),
       ('Takamine', 'JP', 2, 2);

INSERT INTO orders(order_id, date, buyer_id, price)
VALUES (1, '2013-08-05 18:19:03', 3, 1124);

INSERT INTO order_items(order_id, product_id)
VALUES (1, 2);

INSERT INTO product_rates(rate_id, value, product_id, buyer_id)
VALUES (1, 10, 2, 3);