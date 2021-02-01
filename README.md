# finalProjectJava_ST_2020. Music Land - музыкальный магазин
## Table of contents
1. [Music Land](#оглавление)
2. [Technologies](#технологии)
3. [Requirements](#требования)
    * [Application requirements](#приложению)
    * [Application functionality requirements](#функционал)
    * [Database requirements](#БДтребования)
4. [Database scheme](#схема)
5. [Build](#установка)
6. [Access](#доступ)

## <a name="оглавление"></a>Music Land
>Music Land - is an online store where you can buy musical instruments,
> receive bonuses for purchases, win gifts and much more

>The application implements the differentiation of access rights by roles: **Buyer**, **Manager**, **Administrator**.

0.Common
* Log in
* View **Products** 
* View **Products** of each **Producer**
* Change of localization

1.Guest (unregistered user)
* Registration

2.Buyer
* View **Products** by rate
* Add **Products** to cart
* Fill out the **Order** form
* Pay for **Order**
* Rate **Products**
* Change personal information(password,login,balance ect)
* Add/delete/update delivery **Address**
* View all **Orders**
* Use of accumulated bonuses (accrued each time depending on the total cost)

2.Administrator
* Change **Products** information (close/open access, change characteristics)
* View all **Buyers**
* Block/unblock **Buyers**
* Choose a random **Buyer** who made a certain number of **Orders** and send him a letter by email with a bonus for further purchases

3.Manager
* Add/delete **Products**
* Add **Producer**
* View store earnings by date
* View store employees
* Add/delete store employees

## <a name="технологии"></a> Technologies
### The information system is implemented using technologies such as: 
* JavaEE (Java Servlets, JSP);
* JDBC - for database connection;
* Servlet container - [Apache Tomcat 8.5.501](https://tomcat.apache.org/tomcat-8.5-doc/changelog.html);
* [JSTL](https://mvnrepository.com/artifact/javax.servlet/jstl/1.2) - using the standard tag library;
* HTML, CSS, JS, Bootstrap 3 - for web-part
* [Log4J2](https://logging.apache.org/log4j/2.x/maven-artifacts.html) - to track application performance;
* [Project Lombok](https://mvnrepository.com/artifact/org.projectlombok/lombok/1.18.16) -  library for data class simplification;
* [JavaMail](https://mvnrepository.com/artifact/javax.mail/mail/1.4.7) - API to send email;
* [MySQL](https://mvnrepository.com/artifact/mysql/mysql-connector-java/8.0.11) 8.0.11 - SQL Database;
* [TestNG](https://mvnrepository.com/artifact/org.testng/testng/6.14.3) - for testing;
* [Maven](https://maven.apache.org/download.cgi) — as a tool for building a project.

## <a name="требования"></a> Requirements
### <a name="приложению"></a> The following application requirements have been implemented:
* localized interface EN||RU||BE
* event logging (Log4J2)
* design patterns: Factory Method, Command, Observer, Singleton, Proxy ect
* sessions for storing information between requests
* filters: [CashFilter(for cash disabling)](https://github.com/Lizaveta-CR/finalProjectJava_ST_2020/blob/master/src/main/java/by/tsvirko/music_shop/filter/CashFilter.java), [CommandFilter(for checking request/response)](https://github.com/Lizaveta-CR/finalProjectJava_ST_2020/blob/master/src/main/java/by/tsvirko/music_shop/filter/CommandFilter.java),[CookieLocaleFilter(to store language)](https://github.com/Lizaveta-CR/finalProjectJava_ST_2020/blob/master/src/main/java/by/tsvirko/music_shop/filter/CookieLocaleFilter.java), [EncodingFilter (to encode request/response)](https://github.com/Lizaveta-CR/finalProjectJava_ST_2020/blob/master/src/main/java/by/tsvirko/music_shop/filter/EncodingFilter.java), [SecurityFilter (to prevent unauthorized user access to a forbidden resource)](https://github.com/Lizaveta-CR/finalProjectJava_ST_2020/blob/master/src/main/java/by/tsvirko/music_shop/filter/SecurityFilter.java), [XSSPreventionFilter (cross site scripting (xss) prevention)](https://github.com/Lizaveta-CR/finalProjectJava_ST_2020/blob/master/src/main/java/by/tsvirko/music_shop/filter/XSSPreventionFilter.java) 
* JSTL tags
* protection against repeated execution of the request by pressing F5
* custom tags 
* viewing long lists (view **Buyer**'s **Orders**, view **Buyers**)
* validation of input data on the client and on the server
* documentation
* tests. The TestNG technology was used. All services and ConnectionPool have been tested. A separate database was created
### <a name="функционал"></a> The following minimum requirements for the functionality of the application have been implemented:
* authorization (sign in) and logout (sign out) to/from system
* user registration and/or adding an artifact of the system domain
* viewing information
* deletion of information
* adding and modifying information
### <a name="БДтребования"></a>The following minimum requirements for the database have been implemented:
* database access technology - JDBC
* [thread safe pool](https://github.com/Lizaveta-CR/finalProjectJava_ST_2020/tree/master/src/main/java/by/tsvirko/music_shop/dao/pool) (using thread safe collections and libraries java.util.concurrent.*)
* no more than 6-8 tables. Implemented: users (users of the system), buyers (buyers), addresses (addresses of buyers. A separate table has been created to correctly fill out the delivery address with accurate information), products (store products), producers (manufacturers of goods), orders (orders), order_items (order receipt), producer_items (producer-products embedded table), product_rates (product evaluations), countries (countries handbook table), categories (product categories handbook table)
* work with data in the application is carried out using the DAO design pattern
* protection against sql injection
***
## <a name="схема"></a>Database scheme

 ![scheme](https://user-images.githubusercontent.com/56049061/105872094-0df50400-600b-11eb-8e8e-3ad98f4f012d.png)
***
 
## <a name="установка"></a>Application installation instructions:
Requirements for running:
* Java 9 or higher
* MySQL database
* Maven - to create dependencies
* Tomcat - to start server
The rest of the dependencies will be added automatically

To create a database you need to run [scripts](https://github.com/Lizaveta-CR/finalProjectJava_ST_2020/tree/master/sql)
- 1_drop_database.sql
- 2_create_database.sql
- 3_create_tables.sql
- 4_fill_tables.sql

To create a *test* database you need to run [scripts](https://github.com/Lizaveta-CR/finalProjectJava_ST_2020/tree/master/sql/test)
- 1_drop_test_database.sql
- 2_create_test_database.sql
- 3_create_test_tables.sql
- 4_fill_test_ables.sql

 
 ##  <a name="доступ"></a>To access the application you need
 * Run the above scripts
 *To log in as a buyer:
 ```login: elizT, password: elizT```
 * To log in with administrator rights:
 ``` login: administrator, password: administrator```
 * To log in with manager rights:
 ``` login: manager, password: manager```