package com.apollo.repository;

import com.apollo.entity.OptionTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface OptionTableRepository  extends JpaRepository<OptionTable, Long> {
    // Phương thức để tìm tất cả OptionTable dựa trên productId
    List<OptionTable> findByProductId(Long productId);
}
