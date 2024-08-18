package com.apollo.service;

import com.apollo.dto.CommentDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommentService {
    public List<CommentDTO> getCommentsByReviewId(Long reviewId);
}
