package ru.geekbrains.controllers.rest;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.geekbrains.persistence.Cart;
import ru.geekbrains.services.CartService;

@RestController
@RequestMapping("/api/v1/cart")
@AllArgsConstructor
public class CartRestController {

    private CartService cartService;
    private Cart cart;
//
//    @GetMapping
//    public ResponseEntity<?> cartList (Model model) {
//        List<Product> cartProductsList = cartService.getCartListSorted(cart);
//        if (page.getContent().size() > 0) {
//            return ResponseEntity.ok(page.getContent());
//        }
//        return ResponseEntity.badRequest().build();
//    }
//
//    @GetMapping("/add/{productId}")
//    public void addToCart (
//            @PathVariable(name = "productId") Long id,
//            @RequestParam(required = false, name = "quantity") Integer quantity,
//            HttpServletRequest request,
//            HttpServletResponse response) throws IOException {
//            cartService.addProductById(cart, id, quantity);
//            response.sendRedirect(request.getHeader("referer"));
//    }

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
