USE musicShop_db;

INSERT INTO users(login, password, role, name, surname)
VALUES ('admin1', 'e00cf25ad42683b3df678c61f42c6bda', 0, 'Marina', 'Velikaya'),
       ('manager1', 'c240642ddef994358c96da82c0361a58', 1, 'Dasha', 'Volovetz');
DELETE
FROM buyers;
INSERT INTO buyers(buyer_id, email, password, telephone, balance, bonus, name, surname)
VALUES (1, 'elizTs@gmail.com', '4b8ed8ec7f3dfb86fc092485f8f7fb63', 375445684811, 100.10, 0, 'Elizaveta', 'Tsvirko'),
       (2, 'ksuSokol@mail.ru', '53f115c78173b704702ded23e945861f', 375297385012, 119.99, 'Kseniya', 'Sokol'),
       (3, 'kateBest@mail.ru', 'f1b3d4c742ecb604ac0fb2ae39884677', 375334177490, 2000, 'Kate');
INSERT INTO countries(country_id, country_code, country_name, region_id)
VALUES (1, 'AF', 'Afghanistan', 4),
       (2, 'AL', 'Albania', 8),
       (3, 'DZ', 'Algeria', 12),
       (4, 'AS', 'American Samoa', 16),
       (5, 'AD', 'Andorra', 20),
       (6, 'AO', 'Angola', 24),
       (7, 'AI', 'Anguilla', 660),
       (8, 'AG', 'Antigua and Barbuda', 28);
INSERT
INTO addresses(buyer_id, city, street, apartment_number, house_number, country_id)
VALUES (1, 'Minsk', 'Pobeda', 12, 12, 1),
       (2, 'Grodno', 'Lenina', 7, 98, 2);

INSERT INTO instruments(name_id, name)
VALUES (1, 'Guitar'),
       (2, 'Violin'),
       (3, 'Piano');

DELETE
FROM products;
INSERT INTO products(product_id, name_id, description)
VALUES (1, 1,
        'Гитара акустическая Fender CD-60S Natural.Данная версия гитар Classic Design от компании Fender получила ряд замечательных "фишек": топ из массива ели, нижнюю деку и обечайки из махагони, удобный гриф из махагони и гладкую накладку из ореха с закатанными ладами.'),
       (2, 2,
        'Скрипка Cervini HV-100 1/2.Это удобный, качественно собранный инструмент с ясным и насыщенным тембром, который отлично подходит для начинающих скрипачей и учащихся начальных классов музыкальных школВерхняя дека этой скрипки изготовлена из ели, а нижняя дека, обечайка и шейка - из клена. Колки сделаны из твердых пород древесины. Удобный подбородник модели Stradivarius также выполнен из твердых пород древесины.');

DELETE
FROM producers;
INSERT INTO producers(name, country, producer_id)
VALUES ('Takamine', 5, 1);

DELETE
FROM producer_items;
INSERT INTO producer_items(producer_id, product_id, price)
VALUES (1, 1, 100.22),
       (1, 2, 123.99);

DELETE
FROM orders;

INSERT INTO orders(order_id, date, buyer_id,price)
VALUES (1, '2013-08-05 18:19:03', 1, 100.22);

DELETE
FROM order_items;

INSERT INTO order_items(order_id, product_id,amount)
VALUES (1, 1,1);

DELETE
FROM product_rates;

INSERT INTO product_rates(rate_id, value, product_id, buyer_id)
VALUES (1, 10, 1, 1);