package com.apollo.repository;

import com.apollo.dto.order_sumary.OrderSummaryDTO;
import com.apollo.entity.ShopOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShopOrderRepository extends JpaRepository<ShopOrder, Long> {
    List<ShopOrder> findAllByUserIdOrderByIdDesc(Long userId);

    @Query("SELECT new com.apollo.dto.order_sumary.OrderSummaryDTO(so.id, so.orderDate, so.status, so.orderTotal, v.name, so.quantity, v.price) " +
            "FROM ShopOrder so JOIN so.variant v WHERE so.user.id = :userId ORDER BY so.id DESC")
    List<OrderSummaryDTO> findOrderSummariesByUserId(@Param("userId") Long userId);
}
