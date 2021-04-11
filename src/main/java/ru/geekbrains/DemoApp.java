package ru.geekbrains;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.geekbrains.config.AppConfig;
import ru.geekbrains.persistence.Cart;
import ru.geekbrains.persistence.Product;
import ru.geekbrains.service.CartService;
import ru.geekbrains.service.CartServiceImpl;
import ru.geekbrains.service.ProductService;
import ru.geekbrains.service.ProductServiceImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class DemoApp {

    static ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
    static ProductService productService = context.getBean(ProductServiceImpl.class);
    static CartServiceImpl cart;

    public static void main(String[] args) throws IOException {
        System.out.println("\n----------- ТЕСТИРОВАНИЕ КОРЗИНЫ -----------\n");
        cartTest(); // все операции с корзиной и списком продуктов в автоматическом режиме
        System.out.println("\n----- ИНТЕРАКТИВНАЯ РАБОТА С КОРЗИНОЙ ------\n");
        cartInteract(); // операции с корзиной в интерактивном режиме
    }

    private static void cartTest() {
        printList(productService.getProductList(), "СПИСОК ПРОДУКТОВ:");
        printSeparator();

        //вывести продукт с id = 2
        System.out.println("Распечатать: продукт с id=2");
        System.out.println(productService.getProductById(2L));
        printSeparator();

        //удалить продукт с id = 3, добавить - с id = 8, заменить - id = 1 (сделать цену 0.01)
        System.out.println("Продукт с id=3 удалён.");
        productService.deleteById(3L);
        System.out.println("Продукт с id=8 добавлен.");
        productService.saveOrUpdate(new Product(8L, "Product Added", 888.0));
        System.out.println("Продукт с id=1 изменён.");
        productService.saveOrUpdate(new Product(1L, "Product Changed", .01));
        printSeparator();

        //распечатать измененный список продуктов
        printList(productService.getProductList(), "СПИСОК ПРОДУКТОВ:");
        printSeparator();

        //создать корзину
        ProductService productService2 = context.getBean(ProductServiceImpl.class);
        System.out.println("Бин имеет состояние singleton. Новый экземпляр класса не создается.");
        printList(productService2.getProductList(), "НОВЫЙ СПИСОК ПРОДУКТОВ (повторяет первый):");
        printSeparator();

        cart = context.getBean("cartServiceImpl", CartServiceImpl.class);
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
        CartService cart2 = context.getBean("cartServiceImpl", CartServiceImpl.class);
        cart2.addProduct(1L, 3);
        cart2.printCart();
    }

    public static void printSeparator(){
        System.out.println("------------------------------");
    }

    private static void printList(List<?> list, String title) {
        if (title != null && !title.isEmpty()) {
            System.out.println(title);
        }
        for (Object el : list) {
            System.out.println(el.toString());
        }
    }

    private static void cartInteract() throws IOException {
        cart = context.getBean("cartServiceImpl", CartServiceImpl.class);
        cart.setCart(context.getBean("cart", Cart.class));

        System.out.println("Консольное приложение для работы с корзиной. Для справки /?");
        listCommand();
        printSeparator();

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        boolean exit = false;
        while(!exit) {

            System.out.print("Введите команду: ");

            Long prodId = -1L;
            int quantity = -1;

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
                } else if (command.equals("list")) {
                    // распечатать список продуктов
                    printSeparator();
                    printList(productService.getProductList(), "СПИСОК ПРОДУКТОВ:");
                    printSeparator();
                } else if (command.equalsIgnoreCase("new")) {
                    // удалить корзину, создать новую
                    cart = null;
                    cart = context.getBean("cartServiceImpl", CartServiceImpl.class);
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
        System.out.println("Завершить: exit");
    }
}

/* РЕЗУЛЬТАТ (консоль):

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
Бин имеет состояние singleton. Новый экземпляр класса не создается.
НОВЫЙ СПИСОК ПРОДУКТОВ (повторяет первый):
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
Product id = 8  | name = Product Added   | price = 888.0    | quantity = 5   | sum = 4440.0
Product id = 2  | name = Product 2       | price = 20.02    | quantity = 2   | sum = 40.04
Product id = 1  | name = Product Changed | price = 0.01     | quantity = 6   | sum = 0.06
Общая стоимость продуктов в корзине: 4480.1

----- ИНТЕРАКТИВНАЯ РАБОТА С КОРЗИНОЙ ------

Консольное приложение для работы с корзиной. Для справки /?
Распечатать список продуктов: list
Добавить продукт: add [N продукта] [количество]
Удалить продукт: del [N продукта] [количество]
	Если количество товара в корзине < 1, то он удаляется из списка.
Распечатать содержимое корзины: print
Распечатать только стоимость корзины: sum
Удалить корзину и создать новую: new
Завершить: exit
------------------------------
Введите команду: print
------------------------------
Общая стоимость продуктов в корзине: 0.0
------------------------------
Введите команду: list
------------------------------
СПИСОК ПРОДУКТОВ:
Product {id = 1  | name = Product Changed | price = 0.01    }
Product {id = 2  | name = Product 2       | price = 20.02   }
Product {id = 4  | name = Product 4       | price = 444.44  }
Product {id = 5  | name = Product 5       | price = 55.5    }
Product {id = 8  | name = Product Added   | price = 888.0   }
------------------------------
Введите команду: add 8 8
В корзину добавлен товар: Product {id = 8  | name = Product Added   | price = 888.0   } - 8 шт.
Введите команду: add 7 sdf
Неправильный формат команды
Введите команду: add 7 7
Такого товара нет в списке.
Введите команду: add 1 1
В корзину добавлен товар: Product {id = 1  | name = Product Changed | price = 0.01    } - 1 шт.
Введите команду: add 2 2
В корзину добавлен товар: Product {id = 2  | name = Product 2       | price = 20.02   } - 2 шт.
Введите команду: print
------------------------------
Product id = 8  | name = Product Added   | price = 888.0    | quantity = 8   | sum = 7104.0
Product id = 2  | name = Product 2       | price = 20.02    | quantity = 2   | sum = 40.04
Product id = 1  | name = Product Changed | price = 0.01     | quantity = 1   | sum = 0.01
Общая стоимость продуктов в корзине: 7144.05
------------------------------
Введите команду: del 4 4
Такого продукта нет в корзине.
Введите команду: del 8 888
Из корзины удален товар: Product {id = 8  | name = Product Added   | price = 888.0   } - 888 шт.
Введите команду: print
------------------------------
Product id = 2  | name = Product 2       | price = 20.02    | quantity = 2   | sum = 40.04
Product id = 1  | name = Product Changed | price = 0.01     | quantity = 1   | sum = 0.01
Общая стоимость продуктов в корзине: 40.05
------------------------------
Введите команду: exit
Спасибо, что воспользовались нашим интернет-магазином.

Process finished with exit code 0

*/