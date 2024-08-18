package com.apollo.controller;

import com.apollo.dto.CommentDTO;
import com.apollo.payload.response.CommentResponse;
import com.apollo.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/comments")
@AllArgsConstructor
public class CommentController {

    private CommentService commentService;

    @GetMapping("/{review_id}")
    public ResponseEntity<CommentResponse> getCommentsByReviewId(@PathVariable("review_id") Long reviewId){
        CommentResponse commentResponse = new CommentResponse();
        List<CommentDTO> commentDTOList = commentService.getCommentsByReviewId(reviewId);
         commentResponse.setCommentDTOList(commentDTOList);
        return new ResponseEntity<>(commentResponse, HttpStatus.OK);

    }
}
