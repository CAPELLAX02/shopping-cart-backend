package com.capellax.shoppingCart.service.cart;

import com.capellax.shoppingCart.model.Cart;
import com.capellax.shoppingCart.model.User;

import java.math.BigDecimal;

public interface ICartService {

    Cart getCart(Long id);
    void clearCart(Long id);
    BigDecimal getTotalPrice(Long id);
    Cart initializeNewCart(User user);
    Cart getCartByUser(Long userId);

}
