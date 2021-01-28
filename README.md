# finalProjectJava_ST_2020 
## Информационная система реализована с помощью таких технологий, как: Java EE, Servlet, JSP, [JSTL](https://mvnrepository.com/artifact/javax.servlet/jstl/1.2) , [Log4J2](https://logging.apache.org/log4j/2.x/maven-artifacts.html) , [Project Lombok](https://mvnrepository.com/artifact/org.projectlombok/lombok/1.18.16), [JavaMail](https://mvnrepository.com/artifact/javax.mail/mail/1.4.7) ,  [MySQL](https://mvnrepository.com/artifact/mysql/mysql-connector-java/8.0.11) 
### Music Land - музыкальный магазин.
> Магазин музыкальных товаров. Роли: **Покупатель**, **Менеджер**, **Администратор**.
Гость может просматривать как просто доступные **Товары**, так и товары каждого **Производителя** по отдельности. Входить в личный кабинет/регистрироваться. **Покупатель** добавляет **Товары**  в корзину. Затем заполняет форму **Заказа** и оплачивает. Также **Покупатель** может ставить **Оценку** товару, изменять информацию о себе (личные данные, **Адресс** доставки), а также видит все сделанные **Заказы**. За каждый **Заказ** получает бонус, которым может воспользоваться при дальнейшей оплате.
**Администратор** может изменять информацию о **Товарах**, управляет их доступностью. Видит всех **Покупателей**, может блокировать/разблокировать их доступ к сайту. Выбирает случайного **Покупателя**, совершившего определенное число **Заказов** и отправляет ему на почту письмо с бонусом на дальнейшие покупки.
**Менеджер** может добавить/удалить **Товар**. Видит всех работников магазина(удалить/добавить). А также может просмотреть выручку магазина.
### Выполнены такие требования к приложению, как:
* локализованный интерфейс EN||RU||BE
* логирование событий (Log4J2)
* использование шаблонов проектирования: Factory Method, Command, Observer, Singleton, Proxy ect
* использование сессии для хранения информации между запросами
* фильтры: [CashFilter(для отключения кеша)](https://github.com/Lizaveta-CR/finalProjectJava_ST_2020/blob/master/src/main/java/by/tsvirko/music_shop/filter/CashFilter.java), [CommandFilter(проверка запроса/ответа)](https://github.com/Lizaveta-CR/finalProjectJava_ST_2020/blob/master/src/main/java/by/tsvirko/music_shop/filter/CommandFilter.java),[CookieLocaleFilter(хранение информации о языке)](https://github.com/Lizaveta-CR/finalProjectJava_ST_2020/blob/master/src/main/java/by/tsvirko/music_shop/filter/CookieLocaleFilter.java), [EncodingFilter (кодировка запроса/ответа)](https://github.com/Lizaveta-CR/finalProjectJava_ST_2020/blob/master/src/main/java/by/tsvirko/music_shop/filter/EncodingFilter.java), [SecurityFilter (для предотвращения несанкционированного доступа к пользователя к запрещенному ему ресурсу)](https://github.com/Lizaveta-CR/finalProjectJava_ST_2020/blob/master/src/main/java/by/tsvirko/music_shop/filter/SecurityFilter.java), [XSSPreventionFilter (защита от cross site scripting (xss))](https://github.com/Lizaveta-CR/finalProjectJava_ST_2020/blob/master/src/main/java/by/tsvirko/music_shop/filter/XSSPreventionFilter.java) 
* использование тегов библиотеки JSTL
* защита от повторного выполнения запроса нажатием F5
* собственные теги (реализованы как [наследники класса TagSupport(вывод приветственного сообщения, функции)](https://github.com/Lizaveta-CR/finalProjectJava_ST_2020/tree/master/src/main/java/by/tsvirko/music_shop/tag), так и [*.tag(меню, "контейнер" *.html, html head))](https://github.com/Lizaveta-CR/finalProjectJava_ST_2020/tree/master/src/main/webapp/WEB-INF/tags)
* просмотр длинных списков (реализовано: просмотр заказов **Покупателя**, просмотр самих **Покупателей** )
* валидация входных данных на клиенте и на сервере
* документация
* тесты. Использована технология TestNG. Протестированы все сервисы и ConnectionPool. Была создана отдельная БД
### Выполнены такие минимальные требования к функционалу приложению, как:
* авторизация (sign in) и выход (sign out) в/из системы.
* регистрация пользователя и/или добавление артефакта предметной области системы.
* просмотр информации
* удаление информации
* добавление и модификация информации
### Выполнены такие минимальные к БД, как:
* технология доступа к БД  - JDBC
* реализован [потокобезопасный пул](https://github.com/Lizaveta-CR/finalProjectJava_ST_2020/tree/master/src/main/java/by/tsvirko/music_shop/dao/pool) с использованием потокобезопасных коллекций и библиотеки java.util.concurrent.*
* не более 6-8 таблиц. Реализовано: users(пользователи системы), buyers(покупатели), addresses(адреса покупателей. Создана отдельная таблица, чтобы правильно оформить адрес доставки с точной информацией), products(товары магазина), producers(производители товаров), orders(заказы), order_items(чек заказа), producer_items(producer-products embedded таблица), product_rates(оценки товаров), countries(таблица-справочник стран), categories(таблица-справочник категорий товаров)
* работа с данными в приложении осуществляется посредством шаблона DAO 
* реализована защита от sql injection
***
##
 Схема базы данных

 ![scheme](https://user-images.githubusercontent.com/56049061/105872094-0df50400-600b-11eb-8e8e-3ad98f4f012d.png)
***
 
Инструкция по установке приложения:

Для создания базы данных необходимо запустить [скрипты](https://github.com/Lizaveta-CR/finalProjectJava_ST_2020/tree/master/sql)
- 1_drop_database.sql
- 2_create_database.sql
- 3_create_tables.sql
- 4_fill_tables.sql

Для создания *тестовой* базы данных необходимо запустить [скрипты](https://github.com/Lizaveta-CR/finalProjectJava_ST_2020/tree/master/sql/test)
- 1_drop_test_database.sql
- 2_create_test_database.sql
- 3_create_test_tables.sql
- 4_fill_test_ables.sql

 
 ***
 Для достпа к приложению необходимо
 * 
 * 
