package com.apollo.repository;

import com.apollo.entity.OrderDelivery;
import com.apollo.entity.Shipper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDeliveryRepository extends JpaRepository<OrderDelivery, Long> {
    List<OrderDelivery> findByShipper(Shipper shipper);
}
