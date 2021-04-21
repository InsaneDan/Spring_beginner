# ![Spring logo](https://github.com/InsaneDan/InsaneDan/blob/main/spring.png)

### Урок 5. Java Persistence API. Hibernate. Часть 1
<details>
<summary>Java Persistence API. Hibernate. Понятие сущности. Объектно-реляционное отображение. Контекст постоянства (Persistence Context). Менеджер сущностей. Доступ к атрибутам.</summary>
  
1. Создайте сущность Product (Long id, String title, int price) и таблицу в базе данных для хранения объектов этой сущности;
2. Создайте класс ProductDao и реализуйте в нем логику выполнения CRUD-операций над сущностью Product (Product findById(Long id), List"Product" findAll(), void deleteById(Long id), Product saveOrUpdate(Product product)).

Комментарии:

* **Настройкa hibernate**: 1) через конфигурационный класс PersistenceConfig; 2) через hibernate.cfg.xml (закомментирован); 3) через application.properties (закомментировано, требуется Spring Data).
* **Проверено на СУБД**: MySQL и PostgreSQL.
* **Реализация запросов:**
  
  * в основном NamedQuery (показались наиболее удобными);
  * другие варианты – закоментированы в методе *findAllSortedByName* (JPQL Query, NativeQuery, Criteria API - простой поиск+сортировка) и *findById* (find как пример реализации CRUD-операции read).

</details>
