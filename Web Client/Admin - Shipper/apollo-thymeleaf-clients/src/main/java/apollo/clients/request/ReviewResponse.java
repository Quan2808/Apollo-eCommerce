package apollo.clients.request;

import apollo.clients.dto.catalog.ReviewFormDTO;
import apollo.clients.dto.catalog.SummaryDTO;
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
    private List<ReviewFormDTO> reviewDTOList;
    private SummaryDTO summaryDto;
}
