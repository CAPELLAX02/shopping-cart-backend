package com.capellax.shoppingCart.repository;

import com.capellax.shoppingCart.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {



}
