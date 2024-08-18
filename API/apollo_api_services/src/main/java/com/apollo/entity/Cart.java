package com.apollo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "cart", uniqueConstraints = {@UniqueConstraint(name = "user_id_uk", columnNames = "user_id")})
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "cart")
    @JsonIgnore
    private List<CartLine> cartLines;

    @OneToMany(mappedBy = "cart")
    @JsonIgnore
    private List<SaveForLater> saveForLaterList;
}
