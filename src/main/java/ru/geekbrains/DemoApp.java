package ru.geekbrains;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.geekbrains.config.AppConfig;
import ru.geekbrains.persistence.Product;
import ru.geekbrains.service.CartService;
import ru.geekbrains.service.CartServiceImpl;
import ru.geekbrains.service.ProductService;
import ru.geekbrains.service.ProductServiceImpl;

import java.util.List;

public class DemoApp {

    public static void main(String[] args) {

        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        ProductService productService = context.getBean(ProductServiceImpl.class);
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

        CartService cart = context.getBean("cartServiceImpl", CartServiceImpl.class);
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
}

/* РЕЗУЛЬТАТ (консоль):

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
Product id = 1  | name = Product Changed | price = 0.01     | quantity = 3   | sum = 0.03
Product id = 2  | name = Product 2       | price = 20.02    | quantity = 2   | sum = 40.04
Общая стоимость продуктов в корзине: 4480.07
Проверка стоимости корзины (getSum): 4480.07
------------------------------
КОРЗИНА №2
Product id = 1  | name = Product Changed | price = 0.01     | quantity = 3   | sum = 0.03
Общая стоимость продуктов в корзине: 0.03

Process finished with exit code 0

*/