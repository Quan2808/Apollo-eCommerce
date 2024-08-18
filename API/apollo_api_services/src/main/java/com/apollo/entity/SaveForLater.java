package com.apollo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "save_for_later")
public class SaveForLater {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "quantity")
    private int quanity;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    @JsonBackReference(value = "cart_saveForLater")
    private Cart cart;

    @OneToOne
    @JoinColumn(name = "variant_id")
    private Variant variant;
}
