package com.apollo.payload.response;

import com.apollo.dto.CommentDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponse {

    private List<CommentDTO> commentDTOList;
}
