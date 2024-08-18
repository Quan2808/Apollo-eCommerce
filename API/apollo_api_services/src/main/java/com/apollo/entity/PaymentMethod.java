package com.apollo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "payment_method")
public class PaymentMethod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int cartNumber;

    private String nameOnCard;

    private String expirationDate;

    private Boolean defaultPayment;

    @OneToMany(mappedBy = "paymentMethod")
    @JsonBackReference(value = "paymentMethod_shopOrder")
    private Set<ShopOrder> shopOrderSet;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable=false)
    @JsonBackReference(value = "user_paymentMethod")
    private User user;
}
