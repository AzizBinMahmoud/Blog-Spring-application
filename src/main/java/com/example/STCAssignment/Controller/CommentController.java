package com.example.STCAssignment.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.STCAssignment.Service.CommentService;

@RestController
public class CommentController {
	
	private final CommentService commentService;
	
	@Autowired
	public CommentController (CommentService commentService) {
		this.commentService = commentService;
	}
	
	@GetMapping("/getAllComments")
	public ResponseEntity<String> getComments() {
        commentService.getComments();
        return new ResponseEntity<>("\"Successfully saved comments to the database..", HttpStatus.OK);
    }
	
	

}
