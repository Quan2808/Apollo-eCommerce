package com.apollo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="store")

public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String logo;
    private String homeImage;
    private String dealsImage;
    private String dealsSquareImage;
    private String interactiveImage;

    @ManyToOne
    @JoinColumn(name="admin_id")
    private Admin admin;

    @OneToMany(mappedBy = "store")
    @JsonIgnore
    private List<StoreCategory> storeCategoryList;

    @OneToMany(mappedBy = "store")
    @JsonIgnore
    private List<Product> productlist;

}
