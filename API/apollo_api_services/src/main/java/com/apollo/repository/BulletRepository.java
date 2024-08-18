package com.apollo.repository;

import com.apollo.entity.Bullet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BulletRepository extends JpaRepository<Bullet, Long> {
}
