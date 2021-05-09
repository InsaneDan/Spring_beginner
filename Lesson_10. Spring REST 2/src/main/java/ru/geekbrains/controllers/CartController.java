package ru.geekbrains.controllers;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.persistence.Cart;
import ru.geekbrains.service.CartService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/cart")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class CartController {

    private CartService cartService;
    private Cart cart;

    @ModelAttribute("activePage")
    String activePage() {
        return "cart";
    }

    @ModelAttribute("cartItemsBadge")
    Integer cartItemsBadge() {
        return cartService.getItemsAmount(cart);
    }

    @GetMapping
    public String cartList (Model model) {
        model.addAttribute("cartList", cartService.getCartListSorted(cart)); // сортированный список
        model.addAttribute("cartService", cartService);
        model.addAttribute("cart", cart);
        return "cart";
    }

    @GetMapping("/add/{product_id}")
    public void addToCart (
            @PathVariable(name = "product_id") Long id,
            @RequestParam(required = false, name = "q") Integer quantity,
            HttpServletRequest request,
            HttpServletResponse response) throws IOException {
            cartService.addProductById(cart, id, quantity);
            response.sendRedirect(request.getHeader("referer"));
    }

//    @GetMapping("/del/{product_id}")
//    public void delFromToCart (
//            @PathVariable(name = "product_id") Long id,
//            @RequestParam(required = false, name = "q") Integer quantity,
//            HttpServletRequest request,
//            HttpServletResponse response) throws IOException {
//            cartService.addProduct(cart, id, quantity);
//            response.sendRedirect(request.getHeader("referer"));
//    }
}
