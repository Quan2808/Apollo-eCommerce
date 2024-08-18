package com.apollo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "video")
public class Video {
    /* Test */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String videoPath;

    @ManyToOne
    @JoinColumn(name = "variant_id")
    @JsonBackReference(value = "variant_video")
    private Variant variant;

    @ManyToOne
    @JoinColumn(name="store_category_id")
    @JsonBackReference(value = "storeCategory_video")
    private StoreCategory storeCategory;
}
