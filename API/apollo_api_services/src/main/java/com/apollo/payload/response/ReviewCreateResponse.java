package com.apollo.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewCreateResponse {
    private Long id;
    private int star;
    private String title;
    private String content;
    private String image01;
    private String image02;
    private String image03;
    private String image04;
    private String image05;
    private String video;
    private boolean verified_shop;
    private boolean verified_admin;
    private Date create_at;
    private Date update_at;
}
