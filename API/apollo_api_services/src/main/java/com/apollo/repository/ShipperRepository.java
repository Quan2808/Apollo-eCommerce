package com.apollo.repository;

import com.apollo.entity.Shipper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShipperRepository extends JpaRepository<Shipper,Long> {
    Shipper findByEmail(String email);
}
