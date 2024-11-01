package com.capellax.shoppingCart.repository;

import com.capellax.shoppingCart.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);

}
