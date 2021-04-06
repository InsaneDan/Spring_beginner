package ru.geekbrains.listener;

import ru.geekbrains.persistence.Product;
import ru.geekbrains.persistence.ProductRepository;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.math.BigDecimal;

@WebListener
public class BootstrapListener implements ServletContextListener {

    // создание списка продуктов при инициализации сервлета
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ProductRepository productRepository = new ProductRepository();
        for (int i = 1; i < 11; i++) {
            productRepository.saveOrUpdate(new Product(null, "Product " + i,
                    "Description of product " + i, new BigDecimal(i * 100)));
        }
        sce.getServletContext().setAttribute("productRepository", productRepository);
    }
}
