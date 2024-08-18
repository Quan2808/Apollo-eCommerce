package com.apollo.repository;

import com.apollo.entity.Cart;
import com.apollo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findCartByUser(User user);
    Cart findByUserId(Long userId);
}
