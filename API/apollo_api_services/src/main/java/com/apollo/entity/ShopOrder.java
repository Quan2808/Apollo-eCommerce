package com.apollo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "shop_order")
public class ShopOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

//    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "variant_id", nullable = false)
    private Variant variant;

    @Column(name = "order_date")
    private String orderDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "delivery_date")
    private Date deliveryDate;

    @Column(name = "status")
    private String status;

//    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;

//    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "payment_method_id")
    private PaymentMethod paymentMethod;

//    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "shipping_method_id")
    private ShippingMethod shippingMethod;

    private int quantity;

    @Column(name = "order_total")
    private Double orderTotal;

    @OneToMany(mappedBy = "order")
    @JsonIgnore
    private Set<OrderDelivery> orderDeliveries;

    public Double calculateOrderTotal() {
        return quantity * variant.getPrice();
    }
}
