package com.apollo.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SummaryDTO {
    private double rating; //4.7
    private int reviewsTotal;//135050
    private RatingBreakdownDTO ratingBreakdown;
// "summary":{
//  "rating":4.7
//  "reviews_total":27012
//  "rating_breakdown":{
//          "five_star":{
//                "percentage":85
//                "count":22960
//    }
//          "four_star":{...}
//          "three_star":{...}
//          "two_star":{...}
//          "one_star":{...}
//   }

//}
}
