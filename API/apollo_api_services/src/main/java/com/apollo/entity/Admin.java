package com.apollo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "admins")
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "admin_name")
    private String adminName;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    @JsonIgnore
    private String password;

    @Column(name = "balance")
    @JsonIgnore
    private Double balance;

    @Column(name = "role")
    private String role;

    @OneToMany(mappedBy = "admin")
    @JsonIgnore
    private Set<Address> address;

    @OneToMany(mappedBy = "admin")
    @JsonIgnore
    private List<Store> storeList;

    @OneToMany(mappedBy = "admin")
    @JsonIgnore
    private List<Comment> storeComments;
}
