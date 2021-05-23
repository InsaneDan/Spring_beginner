package ru.geekbrains.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.persistence.entities.Product;
import ru.geekbrains.services.ProductService;
import ru.geekbrains.utils.ProductFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

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

        Page<Product> products = productService.findAllFilteredPaged(minPrice, maxPrice, partName, pageIndex, productsPerPage);
        model.addAttribute("productList", products.getContent());
        final int currentPage = products.getPageable().getPageNumber();
        model.addAttribute("currentPage", currentPage + 1);
        model.addAttribute("previousPage", products.getPageable().hasPrevious() ? currentPage : null);
        model.addAttribute("nextPage", products.getTotalPages() > currentPage + 1 ? currentPage + 2 : null);

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
            product = new Product();
        } else {
            product = productService.findProductById(id).get();
        }
        model.addAttribute("product", product);
        model.addAttribute("disabled", view);
        model.addAttribute("referer", request.getHeader("referer"));
        return "product_form";
    }

    @PostMapping("/edit/save")
    public String mergeProduct(@ModelAttribute Product product) {
        productService.saveOrUpdateProduct(product);
        return "redirect:/products";
    }

    @GetMapping("/delete")
    public void deleteProduct(@RequestParam Long id, Model model,
                              HttpServletRequest request,
                              HttpServletResponse response) throws IOException {
        log.debug("Product deleted: " + productService.findProductById(id).toString());
        productService.deleteProductById(id);
//        model.addAttribute("productList", productService.findAllFilteredPaged());
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
        resp = resp + "&partName=" + productFilter.getPartTitle();

        return resp;
    }


}


