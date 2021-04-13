# ![Spring logo](https://github.com/InsaneDan/InsaneDan/blob/main/spring.png)

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
