package com.apollo.service.impl;

import com.apollo.converter.CommentConverter;
import com.apollo.dto.CommentDTO;
import com.apollo.entity.Comment;
import com.apollo.repository.CommentRepository;
import com.apollo.service.CommentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final CommentConverter commentConverter;


    public CommentServiceImpl(CommentRepository commentRepository, CommentConverter commentConverter) {
        this.commentRepository = commentRepository;
        this.commentConverter = commentConverter;
    }

    @Override
    public List<CommentDTO> getCommentsByReviewId(Long reviewId) {
        List<Comment> commentList = commentRepository.getCommentsByReviewId(reviewId);
        List<CommentDTO> commentDtoList = commentConverter.entitiesToDTOs(commentList);
        return commentDtoList;
    }
}
