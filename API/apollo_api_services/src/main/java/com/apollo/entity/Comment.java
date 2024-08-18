package com.apollo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Table(name="comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @ManyToOne
    @JoinColumn(name="review_id")
    @JsonBackReference(value = "comment_review")
    private Review review;

    @ManyToOne
    @JoinColumn(name="admin_id")
    @JsonBackReference(value = "comment_admin")
    private Admin admin;
}
