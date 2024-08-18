package com.apollo.converter;

import com.apollo.dto.CommentDTO;
import com.apollo.entity.Comment;

import java.util.List;

public interface CommentConverter {
    List<CommentDTO> entitiesToDTOs(List<Comment> element);
    CommentDTO entityToDTO(Comment element);
    Comment dtoToEntity(CommentDTO element);
}
