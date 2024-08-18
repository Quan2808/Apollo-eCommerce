package com.apollo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "client_name")
    private String clientName;

    @Column(name = "password")
    @JsonIgnore
    private String password;

    @Column(name = "enabled")
    private boolean enabled;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private Set<Address> address;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number", unique = true)
    private String phoneNumber;

    @Column(name = "role")
    private String role;

    @JsonIgnore
    @OneToOne(mappedBy = "user")
    private Cart cart;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private Set<ShopOrder> shopOrders;

    @OneToMany(mappedBy = "customer")
    @JsonIgnore
    private List<Review> reviewList;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private Set<PaymentMethod> paymentMethods;
}