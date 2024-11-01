package com.capellax.shoppingCart.repository;

import com.capellax.shoppingCart.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    void deleteAllByCartId(Long id);

}
