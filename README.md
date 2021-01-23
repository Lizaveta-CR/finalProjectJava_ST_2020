# finalProjectJava_ST_2020 
### Music Land - музыкальный магазин. Информационная система реализована с помощью технологии Servlet и JSP 
> Магазин музыкальных товаров. Роли: **Покупатель**, **Менеджер**, **Администратор**.
Гость может просматривать как просто доступные **Товары**, так и товары каждого **Производителя** по отдельности. Входить в личный кабинет/регистрироваться. **Покупатель** добавляет **Товары**  в корзину. Затем заполняет форму **Заказа** и оплачивает. Также **Покупатель** может ставить **Оценку** товару, изменять информацию о себе (личные данные, **Адресс** доставки), а также видит все сделанные **Заказы**. За каждый **Заказ** получает бонус, которым может воспользоваться при дальнейшей оплате.
**Администратор** может изменять информацию о **Товарах**, управляет их доступностью. Видит всех **Покупателей**, может блокировать/разблокировать их доступ к сайту. Выбирает случайного **Покупателя**, совершившего определенное число **Заказов** и отправляет ему на почту письмо с бонусом на дальнейшие покупки.
**Менеджер** может добавить/удалить **Товар**. Видит всех работников магазина(удалить/добавить). А также может просмотреть выручку магазина.
### Выполнены такие требования к приложению, как:
* локализованный интерфейс EN||RU
* логирование событий (Log4J2)
* использование шаблонов проектирования: Factory Method, Command, Observer, Singleton, Proxy ect
* использование сессии для хранения информации между запросами
* фильтры: CashFilter(для отключения кеша), CommandFilter(проверка запроса/ответа),CookieLocaleFilter(хранение информации о языке), EncodingFilter (кодировка запроса/ответа), SecurityFilter (для предотвращения несанкционированного доступа к пользователя к запрещенному ему ресурсу), XSSPreventionFilter (защита от cross site scripting (xss)) 
* использование тегов библиотеки JSTL
* защита от повторного выполнения запроса нажатием F5
* собственные теги (реализованы как наследники класса TagSupport(вывод приветственного сообщения, функции), так и *.tag(меню, "контейнер" *.html, html head))
* просмотр длинных списков (реализовано: просмотр заказов **Покупателя**, просмотр самих **Покупателей** )
* валидация входных данных на клиенте и на сервере
* документация
* тесты. Использована технология TestNG. Протестированы все сервисы и ConnectionPool
### Выполнены такие минимальные требования к функционалу приложению, как:
* авторизация (sign in) и выход (sign out) в/из системы.
* регистрация пользователя и/или добавление артефакта предметной области системы.
* просмотр информации
* удаление информации
* добавление и модификация информации
### Выполнены такие минимальные к БД, как:
* технология доступа к БД  - JDBC
* реализован потокобезопасный пул с использованием потокобезопасных коллекций и библиотеки java.util.concurrent.*
* не более 6-8 таблиц. Реализовано: users(пользователи системы), buyers(покупатели), addresses(адреса покупателей. Создана отдельная таблица, чтобы правильно оформить адрес доставки с точной информацией), products(товары магазина), producers(производители товаров), orders(заказы), order_items(чек заказа), producer_items(producer-products embedded таблица), product_rates(оценки товаров), countries(таблица-справочник стран), categories(таблица-справочник категорий товаров)
* работа с данными в приложении осуществляется посредством шаблона DAO 
* реализовать защиту от sql injection
