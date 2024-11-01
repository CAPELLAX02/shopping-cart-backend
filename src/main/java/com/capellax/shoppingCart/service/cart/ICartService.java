package com.capellax.shoppingCart.service.cart;

import com.capellax.shoppingCart.model.Cart;

import java.math.BigDecimal;

public interface ICartService {

    Cart getCart(Long id);
    void clearCart(Long id);
    BigDecimal getTotalPrice(Long id);

}
