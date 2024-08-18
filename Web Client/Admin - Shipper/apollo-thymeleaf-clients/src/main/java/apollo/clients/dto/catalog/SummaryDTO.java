package apollo.clients.dto.catalog;

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
}
