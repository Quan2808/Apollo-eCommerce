package com.apollo.converter.impl;

import com.apollo.converter.CommentConverter;
import com.apollo.dto.CommentDTO;
import com.apollo.entity.Comment;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
@Component
public class CommentConverterImpl implements CommentConverter {
    @Override
    public List<CommentDTO> entitiesToDTOs(List<Comment> element) {
        return element.stream()
                .map(this::entityToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CommentDTO entityToDTO(Comment element) {
        CommentDTO result = new CommentDTO();
        BeanUtils.copyProperties(element, result);
        return result;
    }

    @Override
    public Comment dtoToEntity(CommentDTO element) {
        Comment result = new Comment();
        BeanUtils.copyProperties(element, result);
        return result;
    }
}
