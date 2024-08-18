package com.apollo.repository;

import com.apollo.entity.Cart;
import com.apollo.entity.SaveForLater;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SaveForLaterRepository extends JpaRepository<SaveForLater, Long> {
    List<SaveForLater> findSaveForLaterByCart(Cart cart);
}
