# finalProjectJava_ST_2020. Music Land - музыкальный магазин
## Оглавнение
1. [Music Land](#оглавление)
2. [Использованные технологии](#технологии)
3. [Требования](#требования)
      3. [Требования к приложению](#приложению)
      3. [Требования к функционалу приложения](#функционал)
      3. [Требования к БД](#БДтребования)
4. [Схема БД](#схема)
5. [Установка приложения](#установка)
6. [Доступ к приложению](#доступ)

## <a name="оглавление"></a>Music Land
>Music Land - это интернет-магазин, где можно приобретать музыкальные товары, 
>получать бонусы за покупки, выигрывать подарки и многое другое

>В приложении реализовано разграничение прав доступа по ролям: **Покупатель**, **Менеджер**, **Администратор**.

0.Общее для всех
* Вход в личный кабинет
* Просмотр **Товаров** 
* Просмотр **Товаров** каждого **Производителя**
* Смена локализации
* Выход из аккаунта

1.Гость (незарегестрированный пользователь)
* Регистрация

2.Покупатель
* Просмотр **Товаров** по рейтингу
* Добавление **Товаров** в корзину
* Заполнение формы **Заказа**
* Оплата **Заказа**
* Оценивание **Товаров**
* Изменение личной информации(пароль, логин, почта, баланс и тп)
* Добавление/удаление/изменение **Адресса** доставки
* Просмотр всех **Заказов**
* Использование накопленных бонусов(начисляются каждый раз в зависимости от итоговой стоимости) при оплате

2.Администратор
* Измененение информации о **Товаре** (убрать/открыть доступ, изменить информацию)
* Просмотр всех **Покупателей**
* Блокировать/разблокировать **Покупателя**
* Выбор случайного **Покупателя**, который совершил определенное число **Заказов** и отправление ему на почту письма с бонусом на дальнейшие покупки

3.Менеджер
* Удаление/добавление **Товаров**
* Добавление **Производителя**
* Просмотр выручки магазина по дате
* Просмотр сотрудников магазина
* Удаление/добавление сотрудников магазина

## <a name="технологии"></a> Использованные технологии 
### Информационная система реализована с помощью таких технологий, как: 
* JavaEE (Java Servlets, JSP);
* Для соеденения с базой данных используется JDBC;
* Контейнер сервлетов - Apache Tomcat 8.5.501;
* [JSTL](https://mvnrepository.com/artifact/javax.servlet/jstl/1.2) - использование стандартной библиотеки тегов;
* Для реализации web-части - HTML, CSS, JS, Bootstrap
* Для отслеживания работы приложения и логирования исключительных ситуаций используется [Log4J2](https://logging.apache.org/log4j/2.x/maven-artifacts.html);
* [Project Lombok](https://mvnrepository.com/artifact/org.projectlombok/lombok/1.18.16);
* Для отправки писем используется [JavaMail](https://mvnrepository.com/artifact/javax.mail/mail/1.4.7);
* База данных - [MySQL](https://mvnrepository.com/artifact/mysql/mysql-connector-java/8.0.11) 8.0.11;
* [TestNG](https://mvnrepository.com/artifact/org.testng/testng/6.14.3) - для тестирования;
* [Maven](https://maven.apache.org/download.cgi) — как инструмент для сборки проекта.

## <a name="требования"></a> Требования
### <a name="приложению"></a> Выполнены такие требования к приложению, как:
* локализованный интерфейс EN||RU||BE
* логирование событий (Log4J2)
* использование шаблонов проектирования: Factory Method, Command, Observer, Singleton, Proxy ect
* использование сессии для хранения информации между запросами
* фильтры: [CashFilter(для отключения кеша)](https://github.com/Lizaveta-CR/finalProjectJava_ST_2020/blob/master/src/main/java/by/tsvirko/music_shop/filter/CashFilter.java), [CommandFilter(проверка запроса/ответа)](https://github.com/Lizaveta-CR/finalProjectJava_ST_2020/blob/master/src/main/java/by/tsvirko/music_shop/filter/CommandFilter.java),[CookieLocaleFilter(хранение информации о языке)](https://github.com/Lizaveta-CR/finalProjectJava_ST_2020/blob/master/src/main/java/by/tsvirko/music_shop/filter/CookieLocaleFilter.java), [EncodingFilter (кодировка запроса/ответа)](https://github.com/Lizaveta-CR/finalProjectJava_ST_2020/blob/master/src/main/java/by/tsvirko/music_shop/filter/EncodingFilter.java), [SecurityFilter (для предотвращения несанкционированного доступа пользователя к запрещенному ему ресурсу)](https://github.com/Lizaveta-CR/finalProjectJava_ST_2020/blob/master/src/main/java/by/tsvirko/music_shop/filter/SecurityFilter.java), [XSSPreventionFilter (защита от cross site scripting (xss))](https://github.com/Lizaveta-CR/finalProjectJava_ST_2020/blob/master/src/main/java/by/tsvirko/music_shop/filter/XSSPreventionFilter.java) 
* использование тегов библиотеки JSTL
* защита от повторного выполнения запроса нажатием F5
* собственные теги 
* просмотр длинных списков (просмотр заказов **Покупателя**, просмотр самих **Покупателей** )
* валидация входных данных на клиенте и на сервере
* документация
* тесты. Использована технология TestNG. Протестированы все сервисы и ConnectionPool. Была создана отдельная БД
### <a name="функционал"></a> Выполнены такие минимальные требования к функционалу приложению, как:
* авторизация (sign in) и выход (sign out) в/из системы.
* регистрация пользователя и/или добавление артефакта предметной области системы.
* просмотр информации
* удаление информации
* добавление и модификация информации
### <a name="БДтребования"></a>Выполнены такие минимальные к БД, как:
* технология доступа к БД  - JDBC
* реализован [потокобезопасный пул](https://github.com/Lizaveta-CR/finalProjectJava_ST_2020/tree/master/src/main/java/by/tsvirko/music_shop/dao/pool) с использованием потокобезопасных коллекций и библиотеки java.util.concurrent.*
* не более 6-8 таблиц. Реализовано: users(пользователи системы), buyers(покупатели), addresses(адреса покупателей. Создана отдельная таблица, чтобы правильно оформить адрес доставки с точной информацией), products(товары магазина), producers(производители товаров), orders(заказы), order_items(чек заказа), producer_items(producer-products embedded таблица), product_rates(оценки товаров), countries(таблица-справочник стран), categories(таблица-справочник категорий товаров)
* работа с данными в приложении осуществляется посредством шаблона DAO 
* реализована защита от sql injection
***
## <a name="схема"></a>Схема базы данных

 ![scheme](https://user-images.githubusercontent.com/56049061/105872094-0df50400-600b-11eb-8e8e-3ad98f4f012d.png)
***
 
## <a name="установка"></a>Инструкция по установке приложения:
Требования для запуска:
* Java 9
* MySQL database
* Maven - для создания зависимостей
* Tomcat - для запуска сервера
Остальные зависимости добавяться автоматически

##<a name="скрипты"></a>
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

 
 ##  <a name="доступ"></a>Для доступа к приложению необходимо
 * Запустить вышеуказанные [скрипты](#скрипты)
 * Для входа с правами покупателя:
 ```логин: elizT, пароль: elizT```
 * Для входа с правами администратора:
 ``` логин: administrator, пароль: administrator```
 * Для входа с правами менеджера:
 ``` логин: manager, пароль: manager```