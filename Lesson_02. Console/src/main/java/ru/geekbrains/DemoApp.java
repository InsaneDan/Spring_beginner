package ru.geekbrains;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.List;
import ru.geekbrains.config.AppConfig;
import ru.geekbrains.persistence.Cart;
import ru.geekbrains.persistence.Product;
import ru.geekbrains.service.CartServiceImpl;
import ru.geekbrains.service.ProductService;


@Component
public class DemoApp {

    private final ProductService productService;
    private CartServiceImpl cartService;

    public DemoApp(ProductService productService, CartServiceImpl cartService) {
        this.productService = productService;
        this.cartService = cartService;
    }

    public static void main(String[] args) {
        new AnnotationConfigApplicationContext(AppConfig.class);
    }

    public static void printSeparator(){
        System.out.println("------------------------------");
    }

    private static void printList(List<?> list) {
        System.out.println("СПИСОК ПРОДУКТОВ:");
        for (Object el : list) {
            System.out.println(el.toString());
        }
    }

    @PostConstruct
    private void cartInteract() throws IOException {
        Cart cart = cartService.getNewCart();
        System.out.println("\n----- ИНТЕРАКТИВНАЯ РАБОТА С КОРЗИНОЙ ------\n");

        System.out.println("Консольное приложение для работы с корзиной. Для справки /?");
        listCommand();
        printSeparator();

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        boolean exit = false;
        while(!exit) {

            System.out.print("Введите команду: ");

            Long prodId;
            int quantity;

            String str = in.readLine();
            if (!str.isEmpty()) {
                String[] parts = str.split("\\s");
                String command = parts[0];

                if (command.equalsIgnoreCase("exit")) {
                    exit = true; // флаг - выйти из цикла и завершить работу
                    System.out.println("Спасибо, что воспользовались нашим интернет-магазином.");
                } else if (command.equalsIgnoreCase("/?")) {
                    listCommand(); // справка
                    printSeparator();
                } else if (command.equalsIgnoreCase("test")) {
                    cartTest();
                } else if (command.equals("list")) {
                    // распечатать список продуктов
                    printSeparator();
                    printList(productService.getProductList());
                    printSeparator();
                } else if (command.equalsIgnoreCase("new")) {
                    // удалить корзину, создать новую
                    cart = cartService.getNewCart();
                    System.out.println("Создана новая (пустая) корзина, старая - удалена.");
                } else if (command.equalsIgnoreCase("print")) {
                    printSeparator();
                    cartService.printCart(cart); // распечатать содержимое корзины
                    printSeparator();
                } else if (command.equalsIgnoreCase("sum")) {
                    System.out.println(cartService.getSum(cart)); // распечатать стоимость корзины
                    printSeparator();
                // параметры для удаления и добавления продуктов - должно быть три части
                } else if (parts.length == 3) {
                    // преобразуем данные в нужный формат
                    try {
                        prodId = Long.valueOf(parts[1]);
                        quantity = Integer.parseInt(parts[2]);
                    } catch (NumberFormatException e) {
                        wrongCommand();
                        continue;
                    }

                    if (command.equalsIgnoreCase("add")) {
                        // добавить продукт
                        Product product = productService.getProductById(prodId);
                        if (product != null) {
                            cartService.addProduct(cart, productService.getProductById(prodId), quantity);
                            System.out.println("В корзину добавлен товар: " + productService.getProductById(prodId) + " - " + quantity + " шт.");
                        } else {
                            System.out.println("Такого товара нет в списке.");
                        }
                    } else if (command.equalsIgnoreCase("del")) {
                        // удалить продукт
                        if (cartService.getProductQuantity(cart, prodId) > 0) {
                            cartService.delProduct(cart, productService.getProductById(prodId), quantity);
                            System.out.println("Из корзины удален товар: " + productService.getProductById(prodId) + " - " + quantity + " шт.");
                        } else {
                            System.out.println("Такого продукта нет в корзине.");
                        }
                    }
                } else {
                    wrongCommand();
                }
            }
        }
    }

    private static void wrongCommand() {
        System.out.println("Неправильный формат команды");
    }

    private static void listCommand() {
        System.out.println("Распечатать список продуктов: list");
        System.out.println("Добавить продукт: add [N продукта] [количество]");
        System.out.println("Удалить продукт: del [N продукта] [количество]");
        System.out.println("\tЕсли количество товара в корзине < 1, то он удаляется из списка.");
        System.out.println("Распечатать содержимое корзины: print");
        System.out.println("Распечатать только стоимость корзины: sum");
        System.out.println("Удалить корзину и создать новую: new");
        System.out.println("Тест (все операции с корзиной и продуктами): test");
        System.out.println("Завершить: exit");
    }


    private void cartTest() {
        Cart cart = cartService.getNewCart();
        System.out.println("\n----------- ТЕСТИРОВАНИЕ КОРЗИНЫ -----------\n");

        printList(productService.getProductList());
        printSeparator();

        //вывести продукт с id = 2
        System.out.println("Распечатать: продукт с id=2");
        System.out.println(productService.getProductById(2L));
        printSeparator();

        //удалить продукт с id = 3, добавить - с id = 8, заменить - id = 1 (сделать цену 0.01)
        System.out.println("Продукт с id=3 удалён.");
        productService.deleteById(3L);
        System.out.println("Продукт с id=8 добавлен.");
        productService.saveOrUpdate(new Product(8L, "Product Added", BigDecimal.valueOf(888.00)));
        System.out.println("Продукт с id=1 изменён.");
        productService.saveOrUpdate(new Product(1L, "Product Changed", BigDecimal.valueOf(.01)));
        printSeparator();

        //распечатать измененный список продуктов
        printList(productService.getProductList());
        printSeparator();

        System.out.println("КОРЗИНА №1 (все операции с корзиной - добавить, изменить количество, удалить продукт)");
        // добавить продукты в корзину
        cartService.addProduct(cart, productService.getProductById(1L), 1);
        cartService.addProduct(cart, productService.getProductById(2L), 3);
        cartService.addProduct(cart, productService.getProductById(4L), 9);
        cartService.addProduct(cart, productService.getProductById(5L), 9);
        cartService.addProduct(cart, productService.getProductById(8L), 5);
        // увеличить количество продуктов в корзине (добавить еще продукт с id=1 + 2шт.), итого 3шт.
        cartService.addProduct(cart, productService.getProductById(1L), 2);
        // уменьшить количество одного продукта в корзине (id=2 - 1шт.), итого должно быть 2шт.
        cartService.delProduct(cart, productService.getProductById(2L), 1);
        // удалить продукт из корзины
        cartService.delProduct(cart, productService.getProductById(4L), 999);
        cartService.delProduct(cart, productService.getProductById(5L), null);
        cartService.printCart(cart);
        System.out.println("Проверка стоимости корзины (getSum): " + cartService.getSum(cart));
        printSeparator();

        // создать новую корзину
        System.out.println("КОРЗИНА №2");
        cart = cartService.getNewCart();
        cartService.addProduct(cart, 2L, 2);
        cartService.printCart(cart);

        System.out.println("\n----------- ТЕСТИРОВАНИЕ ЗАВЕРШЕНО -----------\n");

    }

}
