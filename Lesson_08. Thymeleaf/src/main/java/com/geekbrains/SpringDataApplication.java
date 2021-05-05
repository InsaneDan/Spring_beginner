package com.geekbrains;

import com.geekbrains.persistence.entities.Product;
import com.geekbrains.persistence.repositories.ProductRepository;
import com.geekbrains.persistence.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import javax.persistence.NoResultException;
import java.math.BigDecimal;

@SpringBootApplication
//public class SpringDataApplication implements CommandLineRunner {
public class SpringDataApplication {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public SpringDataApplication(UserRepository userRepository, ProductRepository productRepository) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringDataApplication.class, args);
    }

//    @Override
//    public void run(String... args) throws Exception {
///*
//1. Создать сущность «Товар» (id, название, стоимость) и соответствующую таблицу в БД. Заполнить таблицу тестовыми
//   данными (20 записей).
//2. Сделать RestController позволяющий выполнять следующий набор операции над этой сущностью:
//  получение товара по id [ GET .../app/products/{id} ]
//  получение всех товаров [ GET .../app/products ]
//  создание нового товара [ POST .../app/products ]
//  удаление товара по id.[ GET .../app/products/delete/{id} ]
//(**Замечание**: пока делаем немного не по правилам REST API, эта тема будет разбираться на следующих занятиях, поэтому
//удаление выполняется через http-метод GET, а не DELETE)
//3. *К запросу всех товаров добавьте возможность фильтрации по минимальной и максимальной цене (в трех вариантах: товары
//дороже min цены, товары дешевле max цены, или товары, цена которых находится в пределах min-max).
//*/
//
//        //получение товара по id
//        System.out.println("\nПродукт: " + productRepository.findById(1L).orElseThrow(() ->
//                new NoResultException("Товар с указанным id не существует!")));
//
//        //получение всех товаров
//        System.out.println("\nСписок всех продуктов: ");
//        productRepository.findAll().forEach(System.out::println);
//
//        //получение всех товаров с разбивкой по 5 названий на странице
//        int prodPerPage = 5;
//        PageRequest pageRequest = PageRequest.of(0, prodPerPage);
//        Page<Product> products = productRepository.findAll(pageRequest);
//
//        while (true) {
//            System.out.println("\n----------------------------------");
//            System.out.println(products);
//            products.getContent().forEach(System.out::println);
//            if (products.hasNext()) {
//                products = productRepository.findAll(products.nextPageable());
//            } else {
//                break;
//            }
//        }
//
//        System.out.println("\n----------------------------------");
//
//        Product product = null;
//
//        //обновление товара по id
//        System.out.println("Обновление товара по id... 21");
//        product = productRepository.findById(21L).orElseThrow(() ->
//                new NoResultException("Товар с указанным id (21) не существует!"));
//        product.setName("New product name");
//        product.setPrice(BigDecimal.valueOf(222));
//        productRepository.save(product);
//        System.out.println("\nПродукт: " + productRepository.findById(21L).orElseThrow(() ->
//                new NoResultException("Товар с указанным id (21) не существует!")));
//
//        System.out.println("\n----------------------------------");
//
//        //создание нового товара
//        String prodName = "Test product (unique name)";
//        System.out.println("Добавлен новый товар... " + prodName);
//        product = new Product(null, prodName, BigDecimal.valueOf(111));
//        productRepository.save(product);
//        product = productRepository.findProductByName(prodName).get(0); // первый и единственный из списка, т.к. продуктов с таким названием больше нет
//        System.out.println("\nПродукт: " + product);
//
//        System.out.println("\n----------------------------------");
//
//        //удаление товара по id
//        Long prodId = product.getId();
//        System.out.println("Удаление товара... id = " + prodId);
//        productRepository.deleteById(prodId);
//        System.out.println("\nПродукт: " + productRepository.findById(prodId).orElse(null));
//
//        System.out.println("\n----------------------------------");
//
//        // фильтрация по максимальной цене
//        System.out.println("\nПродукты с ценой до 50: ");
//        productRepository.findProductByPriceLessThan(BigDecimal.valueOf(50)).forEach(System.out::println);
//
//        System.out.println("\n----------------------------------");
//
//        // фильтрация по минимальной цене
//        System.out.println("\nПродукты с ценой более 150: ");
//        productRepository.findProductByPriceGreaterThan(BigDecimal.valueOf(150)).forEach(System.out::println);
//
//        // фильтрация по цене в диапазоне min-max
//        System.out.println("\nПродукты в диапазоне цены 50-150: ");
//        productRepository.findProductByPriceBetween(BigDecimal.valueOf(50), BigDecimal.valueOf(150)).forEach(System.out::println);
//
//    }
}
