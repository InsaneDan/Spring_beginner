# ![Spring logo](https://github.com/InsaneDan/InsaneDan/blob/main/spring.png)

### Урок 9. Spring REST. Часть 1

<details>
<summary>Spring REST. HTTP 1.1. CRUD-операции.</summary>

1. Реализуйте REST контроллер для работы с сущностью Product;

<details>
<summary>Комментарии</summary>

* настройка приложения через application.yaml.
* использована H2, развернутая in memory (jdbc:h2:mem:test). Для миграции БД добавлена зависимость Flywaydb, развертывание базы скриптом из файла "resources\db\migration\V1__init.sql". 
* для тестирования REST-запросов добавлен Swagger2.
* Эндпоинты:  
  -  GET - постраничный список товаров (с фильтром по цене и названию товара - @RequestParam); возвращает страницу со списком товаров либо HttpStatus.BAD_REQUEST(400), если список пустой.
  -  GET - получить товар по id (@PathVariable); возвращает товар, если он найден, в противном случае - 404.
  -  POST - добавление товара, PUT - изменение. Т.к. оба метода реализованы через CRUD операцию save(product), то выполняется проверка, чтобы исключить "неправильное" применение. Изменение данных через post и добавление через put возвращает ошибку UNAVAILABLE_FOR_LEGAL_REASONS(451).
  -  DELETE - удаление по id - если товар не найден, возвращается статус NOT_FOUND(404), при успешном удалении возвращается NO_CONTENT(204);
* Т.к. при работе с некоторыми приложениями для работы с REST API (проверял на SoapUI и Postman) возникала ошибка, связанная с неправильным указанием типа контента (вместо contentType:"application/json" отправлял "application/text"), то в аннотацию добавил принудительное преобразование MediaType.APPLICATION_JSON_VALUE. В Swagger значение "application/json" задано по умолчанию.
</details>
</details>