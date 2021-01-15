USE musicShop_db;
# TODO: поменять пароли в связи с хешированием
INSERT INTO users(id, login, name, surname, password, role)
VALUES (1, 'admin1', 'Marya', 'Slavnaya', '100:d8f366c321749fe57af7b23edd6865a4', 0),
       (2, 'manager1', 'Alexey', 'Tsar', 'c240642ddef994358c96da82c0361a58', 1),
       (3, 'elizTs', 'Elizaveta', 'Tsvirko', 'd6b92323c10ad6f5422e5e5bb8e46bf1', 2),
       (6, 'ksuSok', 'Kseniya', 'Sokol', '61f2a3eb792c5e4571502acfa79d8288', 2);
INSERT INTO buyers(buyer_id, email, telephone, balance, enabled)
VALUES
#        (3, 'elizTs@gmail.com', 375445684811, 100.10, true),
(6, 'ksuhaSokol@gmail.com', 375441291821, 220.11, true);

INSERT INTO countries(id, code, name)
VALUES (1, 'BY', 'Belarus'),
       (2, 'US', 'USA'),
       (3, 'RU', 'Russia'),
       (4, 'BR', 'Brazil'),
       (5, 'HK', 'Honk Kong'),
       (6, 'TH', 'Thailand'),
       (7, 'CN', 'China');
INSERT
INTO addresses(buyer_id, country_id, city, zip_code, street, apartment_number, house_number)
VALUES (3, 1, 'Minsk', 220007, 'Pobeda', 12, 12),
       (6, 1, 'Minsk', 220005, 'Pobeda', 11, 29);

DELETE
FROM categories;
INSERT INTO categories(id, name, parent_id)
VALUES
#        (1, 'Instruments', NULL),
#        (2, 'Guitars', 1),
#        (3, 'Acoustic', 2),
#        (4, 'Electro', 2),

#        (5, 'Bowed', 1),

#        (6, 'Bas', 2);
(7, 'Violins', 5);
delete
from categories
where id = 7;
# TODO:change category_id
INSERT INTO products(id, category_id, model, available, description, img, price)
VALUES (1, 3, 'Fender CD-60S Natural', true,
        'Гитара акустическая Fender CD-60S Natural.Данная версия гитар Classic Design от компании Fender получила ряд замечательных "фишек": топ из массива ели,
        нижнюю деку и обечайки из махагони, удобный гриф из махагони и гладкую накладку из ореха с закатанными ладами.',
        'Fender.jpg', 1239.99),
       (2, 4, 'Gibson SG Modern 2019 Blueberry Fade', true,
        'Модели SG серии Modern включают в себя многие наработки современных гитар, полюбившиеся музыкантам, и даже кое-что от "лес полов". Эти гитары имеют корпус из махагони и стильный топ из фигурного клена класса АА - такая комбинация обеспечивает отличный резонанс и сустейн.',
        'Gibson.jpg', 5279.65),
       (3, 7, 'Cervini HV-100 1/2', true,
        'Верхняя дека этой скрипки изготовлена из ели, а нижняя дека, обечайка и шейка - из клена.
Колки сделаны из твердых пород древесины. Удобный подбородник модели Stradivarius также выполнен из твердых
пород древесины. Подгрифник из легкого композитного материала с четырьмя машинками для микроподстройки гарантирует
идеальное интонирование.',
        'Cervini.jpg', 257.40),

       (4, 6, 'Cort Action Bass V Plus TR', true,
        'Корпус инструмента сделан из тополя, а гриф выполнен из клена и крепится на болтах. Накладка на гриф -
палисандровая (24 лада), инкрустированная белыми точками. В бас-гитаре установлены датчики Powersound вида J в бридже и
неке, обеспечивающие сбалансированное звучание. Электроника представлена 2-полосным эквалайзером, регулировками громкости
и тона. Бридж гитары - EB6(5). Колки - литые, фурнитура - хромовая.',
        'bas.jpg', 564.20)
        ,
       (5, 6, 'Homage HEB710RD', true,
        'Корпус гитары с двумя вырезами выполнен из массива твердой древесины. Гриф изготовлен из клена .
Накладка с 21 ладом сделана из палисандра. За строй бас-гитары отвечает колковая механика открытого типа.',
        'bas.jpg', 564.20)
        ,
       (6, 7, 'Brahner BVC-370/MPK 4/4', true,
        'Верхняя дека скрипки выполнена из ели, нижняя дека, обечайка и шейка - из клена. Стильный внешний вид
дополняют гриф, колки, подбородник и подгрифник черного цвета. На скрипке установлены 4 машинки для точной подстройки.',
        'Cervini.jpg', 294.50);

INSERT INTO producers(id, name, country_id)
VALUES (1, 'Takamine', 1),
       (2, 'Homage', 7),
       (3, 'Brahner', 7),
       (4, 'Cervini', 7),
       (5, 'Cremona', 7),
       (6, 'Cort', 4);
#
# DELETE
# FROM producer_items;
INSERT INTO producer_items(producer_id, product_id)
VALUES
#        (1, 1),
#        (1, 2),
#        (2, 5),
#        (3, 6),
(4, 3);
#        (5, 7),
#        (6, 4);
# DELETE
# FROM producer_items
# WHERE producer_id = 5;
#

INSERT
INTO orders(date, buyer_id, price)
VALUES
#        ('2013-08-05 18:19:03', 3, 1239.99),
('2013-08-05 18:19:03', 6, 1239.99);

DELETE
FROM products
WHERE id = 10;

INSERT INTO order_items(id, product_id, amount, price)
VALUES (1, 1, 1, 1239.99),
       (5, 2, 1, 1219);
#
DELETE
FROM users
WHERE id = 25;

# 100:0412c7a255f4c5aa7d64e5be52b6f4d5
#
# INSERT INTO product_rates(rate_id, value, product_id, buyer_id)
# VALUES (1, 10, 1, 1);
# TODO:!!!!!

SELECT pr_it.producer_id, pr.name, pr.country_id
FROM producer_items as pr_it
         INNER JOIN producers as pr
                    ON pr_it.producer_id = pr.id
WHERE pr_it.product_id = 1