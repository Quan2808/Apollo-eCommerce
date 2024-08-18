package com.apollo.payload.response;

import com.apollo.dto.ReviewDTO;
import com.apollo.dto.SummaryDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewResponse {
    private List<ReviewDTO> reviewDTOList;
    private SummaryDTO summaryDto;
}
