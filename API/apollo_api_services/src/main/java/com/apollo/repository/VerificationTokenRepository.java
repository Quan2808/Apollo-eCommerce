package com.apollo.repository;

import com.apollo.entity.Shipper;
import com.apollo.entity.User;
import com.apollo.entity.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken,Long> {
    VerificationToken findByUser(User user);
    VerificationToken findByShipper(Shipper shipper);
    VerificationToken findByToken(String token);
}
