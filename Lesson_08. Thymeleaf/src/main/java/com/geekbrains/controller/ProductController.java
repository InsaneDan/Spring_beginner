package com.geekbrains.controller;

import com.geekbrains.persistence.entities.Product;
import com.geekbrains.service.ProductService;
import com.geekbrains.utils.ProductFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @ModelAttribute("activePage")
    String activePage() {
        return "products";
    }

    @GetMapping({"", "/{pageIndex}"})
    public String showAll(Model model,
                          @PathVariable(required = false) Integer pageIndex,
                          @RequestParam(defaultValue = "0") BigDecimal minPrice,
                          @RequestParam(defaultValue = "1000000") BigDecimal maxPrice,
                          @RequestParam(defaultValue = "") String partName,
                          @RequestParam(defaultValue = "5") Integer productsPerPage) {


        model.addAttribute("productFilter", new ProductFilter(minPrice, maxPrice, partName));

        // если minPrice меньше 0
        if (minPrice.compareTo(BigDecimal.ZERO) < 0) minPrice = BigDecimal.ZERO;
        // если maxPrice больше 1000000
        if (maxPrice.compareTo(BigDecimal.valueOf(1000000)) > 0 ) maxPrice = BigDecimal.valueOf(1000000);
        // если не указан номер страницы, то показываем 1-ю страницу с дефолтными значениями
        if (pageIndex == null) pageIndex = 1;
        // если номер страницы = 0, то выводим весь список товаров (только в учебных целях)
        if (pageIndex == 0) {
            List<Product> productsPlain = productService.getProductList();
            model.addAttribute("productList", productsPlain);
        } else {
            Page<Product> products = productService.getProductsFiltered(minPrice, maxPrice, partName, pageIndex, productsPerPage);
            model.addAttribute("productList", products.getContent());
            final int currentPage = products.getPageable().getPageNumber();
            model.addAttribute("currentPage", currentPage + 1);
            model.addAttribute("previousPage", products.getPageable().hasPrevious() ? currentPage : null);
            model.addAttribute("nextPage", products.getTotalPages() > currentPage + 1 ? currentPage + 2 : null);
        }
        model.addAttribute("productsPerPage", productsPerPage);

        return "products";
    }

    @GetMapping("edit")
    public String editProduct(@RequestParam(required = false) Long id,
                              @RequestParam(required = false) Boolean view,
                              HttpServletRequest request,
                              Model model) {
        Product product;
        if (id == null) {
            product = new Product(null, null, null);
        } else {
            product = productService.getProductById(id);
        }
        model.addAttribute("product", product);
        model.addAttribute("disabled", view);
        model.addAttribute("referer", request.getHeader("referer"));
        return "product_form";
    }

    @PostMapping("/edit/save")
    public String mergeProduct(@ModelAttribute Product product) {
        productService.saveOrUpdate(product);
        return "redirect:/products";
    }

    @GetMapping("/delete")
    public void deleteProduct(@RequestParam Long id, Model model,
                                HttpServletRequest request,
                                HttpServletResponse response) throws IOException {
        logger.debug("Product deleted: " + productService.getProductById(id).toString());
        productService.deleteById(id);
        model.addAttribute("productList", productService.getProductList());
        response.sendRedirect(request.getHeader("referer"));
    }

    @PostMapping("/filter")
    public String setFilter(@ModelAttribute("productFilter") ProductFilter productFilter, Model model) {

//        String resp = request.getHeader("referer");
        String resp = "redirect:/products/1";
        resp = resp + "?minPrice=";
        if (productFilter.getMinPrice() != null) resp = resp + productFilter.getMinPrice(); // без проверки - NumberFormatException
        resp = resp + "&maxPrice=";
        if (productFilter.getMaxPrice() != null) resp = resp + productFilter.getMaxPrice(); // без проверки - NumberFormatException
        resp = resp + "&partName=" + productFilter.getPartName();

        return resp;
    }


}
