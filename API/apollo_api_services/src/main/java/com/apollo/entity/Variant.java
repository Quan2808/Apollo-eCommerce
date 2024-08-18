package com.apollo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "variant")
public class Variant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String skuCode;
    private int stockQuantity;
    private double weight;
    private double price;
    private double salePrice;
    private String img;
    private Boolean isDeleted;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonBackReference
    private Product product;

    @OneToMany(mappedBy = "variant")
    @JsonIgnore
    private List<Image> images;

    @OneToMany(mappedBy = "variant")
    @JsonIgnore
    private List<Video> videos;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "variant_optionvalue",
            joinColumns = @JoinColumn(name = "variant_id"),
            inverseJoinColumns = @JoinColumn(name = "optionvalue_id"))
    @JsonIgnore
    private List<OptionValue> optionValues;

    @OneToOne(mappedBy = "variant")
    private CartLine cartLine;

    @OneToOne(mappedBy = "variant")
    private SaveForLater saveForLater;

    @OneToMany(mappedBy = "variant")
    @JsonIgnore
    private List<Review> reviews;

    @OneToMany(mappedBy = "variant")
    @JsonIgnore
    private Set<ShopOrder> shopOrders;
}
