USE musicShopTest_db;

INSERT INTO users(id, login, name, surname, password, role)
VALUES
       (1, 'manager1', 'Alexey', 'Tsar', '100:8cd14996245d6b31c3b464138e26e68e', 1),
       (2, 'elizTs', 'Elizaveta', 'Tsvirko', '100:0b76f57f15f6f665af455bef05175f30', 2),
       (3, 'ksuSok', 'Kseniya', 'Sokol', '100:e3d5ccdea4363fa6eefc35b1f3206f9d', 2),
       (4, 'likSki', 'Veronika', 'Svet', '100:e3d5ccdea4363fa6eefc35b1f3206f9d', 2);
INSERT INTO buyers(buyer_id, email, telephone, balance, enabled)
VALUES
       (2, 'elizTs@gmail.com', 375445684811, 8000, true),
       (3, 'ksuhaSokol@gmail.com', 375441291821, 6000, true),
       (4, 'likaSki@gmail.com', 375447685098, 6000, true);


INSERT INTO countries(id, code, name)
VALUES (1, 'BY', 'Belarus'),
       (2, 'US', 'USA');

INSERT
INTO addresses(buyer_id, country_id, city, zip_code, street, apartment_number, house_number)
VALUES (2, 1, 'Minsk', 220007, 'Pobeda', 12, 12),
       (3, 1, 'Minsk', 220005, 'Pobeda', 11, 29);

INSERT INTO categories(id, name, parent_id)
VALUES (1, 'Instruments', NULL),
       (2, 'Guitars', 1),
       (3, 'Acoustic', 2),
       (4, 'Electro', 2),
       (5, 'Violins', 1);


INSERT
INTO products(id, category_id, model, available, description, img, price)
VALUES (1, 3, 'Fender CD-60S Natural', true,
        'Гитара акустическая Fender CD-60S Natural.Данная версия гитар Classic Design от компании Fender получила ряд замечательных "фишек": топ из массива ели,
        нижнюю деку и обечайки из махагони, удобный гриф из махагони и гладкую накладку из ореха с закатанными ладами.',
        'Fender CD-60S Natural.jpeg', 1239.99),
       (2, 4, 'Gibson SG Modern 2019 Blueberry Fade', true,
        'Модели SG серии Modern включают в себя многие наработки современных гитар, полюбившиеся музыкантам, и даже кое-что от "лес полов". Эти гитары имеют корпус из махагони и стильный топ из фигурного клена класса АА - такая комбинация обеспечивает отличный резонанс и сустейн.',
        'SG Modern 2019 Blueberry Fade.jpeg', 5279.65);

INSERT INTO producers(id, name, country_id)
VALUES (1, 'Takamine', 1),
       (2, 'Gibson', 2);

INSERT INTO producer_items(producer_id, product_id)
VALUES (1, 1),
       (2, 2);


INSERT INTO orders(id, date, buyer_id, price)
VALUES (1, '2013-08-05 18:19:03', 2, 680.00),
       (2, '2013-08-05 18:19:03', 3, 5279.65);

INSERT INTO order_items(id, product_id, amount, price)
VALUES (1, 1, 1, 680.00),
       (2, 2, 1, 5279.65);

INSERT INTO product_rates(id, mark, product_id, buyer_id)
VALUES (1, 10, 1, 2),
       (2, 9, 2, 3);
DELETE FROM  orders;
DELETE FROM products WHERE id =20;