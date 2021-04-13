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
import ru.geekbrains.persistence.Product;
import ru.geekbrains.service.CartServiceImpl;
import ru.geekbrains.service.ProductService;

@Component
public class DemoApp {

    private final ProductService productService;
    private CartServiceImpl cart;

    public DemoApp(ProductService productService, CartServiceImpl cart) {
        this.productService = productService;
        this.cart = cart;
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
                    cart = null;
                    cart = new AnnotationConfigApplicationContext(AppConfig.class).getBean("cartServiceImpl", CartServiceImpl.class);
                    System.out.println("Создана новая (пустая) корзина, старая - удалена.");
                } else if (command.equalsIgnoreCase("print")) {
                    printSeparator();
                    cart.printCart(); // распечатать содержимое корзины
                    printSeparator();
                } else if (command.equalsIgnoreCase("sum")) {
                    System.out.println(cart.getSum()); // распечатать стоимость корзины
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
                            cart.addProduct(productService.getProductById(prodId), quantity);
                            System.out.println("В корзину добавлен товар: " + productService.getProductById(prodId) + " - " + quantity + " шт.");
                        } else {
                            System.out.println("Такого товара нет в списке.");
                        }
                    } else if (command.equalsIgnoreCase("del")) {
                        // удалить продукт
                        if (cart.getProductQuantity(prodId) > 0) {
                            cart.delProduct(productService.getProductById(prodId), quantity);
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
        cart.addProduct(productService.getProductById(1L), 1);
        cart.addProduct(productService.getProductById(2L), 3);
        cart.addProduct(productService.getProductById(4L), 9);
        cart.addProduct(productService.getProductById(5L), 9);
        cart.addProduct(productService.getProductById(8L), 5);
        // увеличить количество продуктов в корзине (добавить еще продукт с id=1 + 2шт.), итого 3шт.
        cart.addProduct(productService.getProductById(1L), 2);
        // уменьшить количество одного продукта в корзине (id=2 - 1шт.), итого должно быть 2шт.
        cart.delProduct(productService.getProductById(2L), 1);
        // удалить продукт из корзины
        cart.delProduct(productService.getProductById(4L), 999);
        cart.delProduct(productService.getProductById(5L), null);
        cart.printCart();
        System.out.println("Проверка стоимости корзины (getSum): " + cart.getSum());
        printSeparator();

        // создать новую корзину
        System.out.println("КОРЗИНА №2");
        cart.setNewCart();
        cart.addProduct(2L, 2);
        cart.printCart();

        System.out.println("\n----------- ТЕСТИРОВАНИЕ ЗАВЕРШЕНО -----------\n");

    }

}

/* РЕЗУЛЬТАТ (консоль):

----- ИНТЕРАКТИВНАЯ РАБОТА С КОРЗИНОЙ ------

Консольное приложение для работы с корзиной. Для справки /?
Распечатать список продуктов: list
Добавить продукт: add [N продукта] [количество]
Удалить продукт: del [N продукта] [количество]
	Если количество товара в корзине < 1, то он удаляется из списка.
Распечатать содержимое корзины: print
Распечатать только стоимость корзины: sum
Удалить корзину и создать новую: new
Тест (все операции с корзиной и продуктами): test
Завершить: exit
------------------------------
Введите команду: test

----------- ТЕСТИРОВАНИЕ КОРЗИНЫ -----------

СПИСОК ПРОДУКТОВ:
Product {id = 1  | name = Product 1       | price = 110.05  }
Product {id = 2  | name = Product 2       | price = 20.02   }
Product {id = 3  | name = Product 3       | price = 300.0   }
Product {id = 4  | name = Product 4       | price = 444.44  }
Product {id = 5  | name = Product 5       | price = 55.5    }
------------------------------
Распечатать: продукт с id=2
Product {id = 2  | name = Product 2       | price = 20.02   }
------------------------------
Продукт с id=3 удалён.
Продукт с id=8 добавлен.
Продукт с id=1 изменён.
------------------------------
СПИСОК ПРОДУКТОВ:
Product {id = 1  | name = Product Changed | price = 0.01    }
Product {id = 2  | name = Product 2       | price = 20.02   }
Product {id = 4  | name = Product 4       | price = 444.44  }
Product {id = 5  | name = Product 5       | price = 55.5    }
Product {id = 8  | name = Product Added   | price = 888.0   }
------------------------------
КОРЗИНА №1 (все операции с корзиной - добавить, изменить количество, удалить продукт)
Product id = 8  | name = Product Added   | price = 888.0    | quantity = 5   | sum = 4440.0
Product id = 2  | name = Product 2       | price = 20.02    | quantity = 2   | sum = 40.04
Product id = 1  | name = Product Changed | price = 0.01     | quantity = 3   | sum = 0.03
Общая стоимость продуктов в корзине: 4480.07
Проверка стоимости корзины (getSum): 4480.07
------------------------------
КОРЗИНА №2
Product id = 2  | name = Product 2       | price = 20.02    | quantity = 2   | sum = 40.04
Общая стоимость продуктов в корзине: 40.04

----------- ТЕСТИРОВАНИЕ ЗАВЕРШЕНО -----------
*/