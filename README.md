# ![Spring logo](https://github.com/InsaneDan/InsaneDan/blob/main/spring.png)

### 1. Основы Java EE
<details>
<summary>Обзор Java EE-компонентов и введение в web-технологии. Что такое сервлеты. Слушатели. Фильтры.</summary>
  
1. Установить сервер приложений TomCat или Wildfly (либо любой другой по выбору);
2. Создать и запустить новый проект по инструкции из данной методички.
3. Создайте класс Product с полями (id, title, cost).
4. Реализуйте сервлет выводящий на страницу список из 10 продуктов (создаете продукты в момент обработки запроса).

Сервер приложений: WildFly-23.0.0.Final (старт сервера через stanalone.bat)
Реализация: HttpServlet + JSP (± подобие JPA, т.к. нет подключения к БД)
Для установки на сервер приложений - Run Maven goal: clean install wildfly:deploy

URL: http://127.0.0.1:8080/webapp/http-servlet - основные методы HttpServlet
URL: http://127.0.0.1:8080/webapp/product - список продуктов

Комментарии:
- Дескриптор развертывания. Файл web.xml содержит только служебную информацию, маппинг не прописан (используются аннотации @WebServlet(urlPatterns = "..."))
- Вывод списка продуктов: сервлет наследуется от HttpServlet, представление (persistence) - вынесено в отдельные классы (продукт и репозиторий). Для задания испольуются методы только для сохранения и получения списка продуктов из репозитория.
- Список продуктов для репозитория создается при инициализации сервлета (срабатывает ServletContextListener).
- Вывод страницы на сервере приложений с помощью JSP. При формировании страницы импортируется <head> и <navbar> из отдельных jsp-файлов. Страницы созданы с использованием css Bootstrap v4.3.
- SimpleHttpServlet - формирует html-текст для отображения страницы (HttpServletResponse) "вручную", страница содержит основные методы класса.
</details>

### 2. Введение в Spring
<details>
<summary>Введение в Spring. Dependency Injection и Inversion of Control. Бины. Spring Context. Конфигурирование.</summary>
  
1. Есть класс Product (id, название, цена). Товары хранятся в бине ProductRepository, в виде List<Product>, при старте в него нужно добавить 5 любых товаров.
2. ProductRepository позволяет получить весь список или один товар по id. Создаем бин Cart, в который можно добавлять и удалять товары по id.
3. Написать консольное приложение, позволяющее управлять корзиной.
4. При каждом запросе корзины из контекста, должна создаваться новая корзина.

Комментарии:
- компонент ProductRepository - является singletone; Cart - @Scope("prototype");
- наполнение productRepository товарами после создания бина (@PostConstruct);
- корзина в виде Map: ключ - продукты, значение - количество продукта в корзине;
- в CartServiceImpl использована аннотация @Autowired для метода setProductRepository. По большому счету внедрение продуктового репозитория в корзину не требуется - экспериментировал с @Autowired.
- для запуска - DemoApp.
</details>


### Урок 3. Spring MVC
<details>
<summary>Spring MVC. Контроллеры. Работа с формами. Представления. Конфигурирование Spring MVC. Контекст Spring MVC.</summary>
  
1. Разобраться с примером проекта на Spring MVC.
2. Создать класс Товар (Product), с полями id, title, cost.
3. Товары необходимо хранить в репозитории (класс, в котором в виде List<Product> хранятся товары). Репозиторий должен уметь выдавать список всех товаров и товар по id.
4. Сделать форму для добавления товара в репозиторий и логику работы этой формы.
5. Сделать страницу, на которой отображаются все товары из репозитория.

Комментарии:

</details>


### Урок 4. Spring Boot
<details>
<summary>Spring Boot. Архитектура. Модель. Обзор решений</summary>
  
1. Перенести функциональность, реализованную на прошлом занятии, на платформу Spring Boot.

Комментарии:
...

</details>

### Урок 5. Java Persistence API. Hibernate. Часть 1
<details>
<summary></summary>
  

Комментарии:

</details>

### Урок 6. Java Persistence API. Hibernate. Часть 2
<details>
<summary></summary>
  

Комментарии:

</details>

### Урок 7. Spring Data
<details>
<summary></summary>
  

Комментарии:

</details>

### Урок 8. Thymeleaf
<details>
<summary></summary>
  

Комментарии:

</details>

### Урок 9. Spring REST. Часть 1
<details>
<summary></summary>
  

Комментарии:

</details>

### Урок 10. Spring REST. Часть 2
<details>
<summary></summary>
  

Комментарии:

</details>

### Урок 11. Spring Security
<details>
<summary></summary>
  

Комментарии:

</details>

### Урок 12. Практика
<details>
<summary></summary>
  

Комментарии:

</details>
