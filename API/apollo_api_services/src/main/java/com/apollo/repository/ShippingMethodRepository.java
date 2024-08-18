package com.apollo.repository;

import com.apollo.entity.ShippingMethod;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ShippingMethodRepository extends JpaRepository<ShippingMethod, Long> {
}
