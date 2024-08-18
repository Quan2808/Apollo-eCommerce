package com.apollo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "store_category")
public class StoreCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String heroImage;
    private String squareImage;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    @ManyToOne
    @JoinColumn(name = "parent_store_Category_Id")
    private StoreCategory parentStoreCategory;

    @OneToMany(mappedBy = "storeCategory")
    private List<Product> productList;

    @OneToMany(mappedBy = "storeCategory")
    @JsonIgnore
    private List<Image> imageList;

    @OneToMany(mappedBy = "storeCategory")
    @JsonIgnore
    private List<Video> videoList;

}

