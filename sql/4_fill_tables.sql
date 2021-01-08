USE musicShop_db;
# TODO: поменять пароли в связи с хешированием
INSERT INTO users(id, login, name, surname, password, role)
VALUES (1, 'admin1', 'Marya', 'Slavnaya', 'e00cf25ad42683b3df678c61f42c6bda', 0),
       (2, 'manager1', 'Alexey', 'Tsar', 'c240642ddef994358c96da82c0361a58', 1),
       (3, 'elizTs', 'Elizaveta', 'Tsvirko', 'd6b92323c10ad6f5422e5e5bb8e46bf1', 2),
       (6, 'ksuSok', 'Kseniya', 'Sokol', '61f2a3eb792c5e4571502acfa79d8288', 2);
INSERT INTO buyers(buyer_id, email, telephone, balance, enabled)
VALUES
#        (3, 'elizTs@gmail.com', 375445684811, 100.10, true),
(6, 'ksuhaSokol@gmail.com', 375441291821, 220.11, true);

INSERT INTO countries(id, code, name)
VALUES (1, 'BY', 'Belarus');

INSERT
INTO addresses(buyer_id, country_id, city, zip_code, street, apartment_number, house_number)
VALUES (3, 1, 'Minsk', 220007, 'Pobeda', 12, 12),
       (6, 1, 'Minsk', 220005, 'Pobeda', 11, 29);

DELETE
FROM categories;
INSERT INTO categories(id, name, parent_id)
VALUES (1, 'Instruments', NULL),
       (2, 'Guitars', 1),
       (3, 'Acoustic', 2),
       (4, 'Electro', 2),
       (5, 'Violins', 1);
# TODO:change category_id
INSERT
INTO products(id, category_id, model, available, description, img, price)
VALUES (1, 3, 'Fender CD-60S Natural', true,
        'Гитара акустическая Fender CD-60S Natural.Данная версия гитар Classic Design от компании Fender получила ряд замечательных "фишек": топ из массива ели,
        нижнюю деку и обечайки из махагони, удобный гриф из махагони и гладкую накладку из ореха с закатанными ладами.',
        'Fender CD-60S Natural.jpeg', 1239.99),
       (2, 4, 'Gibson SG Modern 2019 Blueberry Fade', true,
        'Модели SG серии Modern включают в себя многие наработки современных гитар, полюбившиеся музыкантам, и даже кое-что от "лес полов". Эти гитары имеют корпус из махагони и стильный топ из фигурного клена класса АА - такая комбинация обеспечивает отличный резонанс и сустейн.',
        'SG Modern 2019 Blueberry Fade.jpeg', 5279.65);
UPDATE products
SET img='Gibson.jpg'
WHERE id = 2;
INSERT INTO producers(id, name, country_id)
VALUES (1, 'Takamine', 1);
#
# DELETE
# FROM producer_items;
INSERT INTO producer_items(producer_id, product_id)
VALUES (1, 1);
#
#

INSERT INTO orders(date, buyer_id, price)
VALUES
#        ('2013-08-05 18:19:03', 3, 1239.99),
('2013-08-05 18:19:03', 6, 1239.99);

DELETE
FROM products WHERE id=10;

INSERT INTO order_items(id, product_id, amount, price)
VALUES (1, 1, 1, 1239.99),
       (5, 2, 1, 1219);
#
DELETE
FROM users WHERE id=11;
#
# INSERT INTO product_rates(rate_id, value, product_id, buyer_id)
# VALUES (1, 10, 1, 1);
# TODO:!!!!!

