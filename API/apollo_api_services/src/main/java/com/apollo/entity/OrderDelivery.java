package com.apollo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "order_delivery")
public class OrderDelivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_delivery_id")
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "order_id")
    private ShopOrder order;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "shipper_id", referencedColumnName = "id")
    private Shipper shipper;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "assigned_date")
    private Date assignedDate;

    @Column(name = "status")
    private String status;

    @Column(name = "inducement")
    private String inducement;
}
