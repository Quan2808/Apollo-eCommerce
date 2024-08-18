package com.apollo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(length = 2000)
    private String description;

    private String mainPicture;
    private String status;
    private Date createAt;
    private Date updatedAt;

    @ManyToOne
    @JoinColumn (name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name ="store_category_id")
    @JsonIgnore
    private StoreCategory storeCategory;

    @ManyToOne
    @JoinColumn(name="store_id")
    private Store store;

    @OneToMany(mappedBy = "product")
//    @JsonIgnore
    private List<OptionTable> optionTables;

    @OneToMany(mappedBy = "product")
    @JsonManagedReference
    private List<Variant> variants;

    @OneToMany(mappedBy = "product")
    @JsonIgnore
    private List<ProductAttribute> productAttributes;

    @OneToMany(mappedBy = "product")
    @JsonIgnore
    private List<HashTag> hashTagList;

    @OneToMany(mappedBy = "product")
    @JsonIgnore
    private List<Bullet> bulletList;
}
