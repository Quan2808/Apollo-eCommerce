package apollo.clients.dto.catalog;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RatingDTO {
    private double percentage;
    private int count;
}
