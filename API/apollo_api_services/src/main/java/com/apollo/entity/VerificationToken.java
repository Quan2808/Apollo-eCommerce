package com.apollo.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "verification_token")
public class VerificationToken {
    private static final int EXPIRATION = 60 * 24;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;

    @OneToOne(targetEntity = User.class)
    @JoinColumn(nullable = true, name = "user_id")
    private User user;

    @OneToOne(targetEntity = Shipper.class)
    @JoinColumn(nullable = true, name = "shipper_id")
    private Shipper shipper;

    private Date expiryDate;

    public VerificationToken(Shipper shipper) {
        this.shipper = shipper;
        this.token = UUID.randomUUID().toString();
        this.expiryDate = calculateExpiryDate(EXPIRATION); // Using the existing expiration time constant
    }

    private Date calculateExpiryDate(int expiryTimeInMinutes) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }
}
