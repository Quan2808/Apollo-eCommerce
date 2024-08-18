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
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String attribute;

    @ManyToOne
    @JoinColumn(name ="parent_Category_Id")
    private Category parentCategory;

    @OneToMany ( mappedBy = "category")
    @JsonIgnore
    private List<Product> products;
}
