package com.example.STCAssignment.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.example.STCAssignment.Model.Comment;
import com.example.STCAssignment.Model.Post;
import com.example.STCAssignment.Repository.CommentRepository;
import com.example.STCAssignment.Repository.PostRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class CommentService {
	
    private static final String EXTERNAL_API_URL = "https://gorest.co.in/public/v2/comments?page=4&per_page=100"; // Replace with the actual API URL

	private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final RestTemplate restTemplate;

    @Autowired
    public CommentService(PostRepository postRepository, CommentRepository commentRepository, RestTemplate restTemplate) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.restTemplate = restTemplate;
    }
    
    public void getComments() {
    	
    	ResponseEntity<String> response = restTemplate.getForEntity(EXTERNAL_API_URL, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode root = objectMapper.readTree(response.getBody());
                List<Comment> Comments = new ArrayList<>();

                for (JsonNode commentNode : root) {
                    
                    if (postRepository.existsById(commentNode.get("post_id").asLong())) {
                    
                        Comment comment = new Comment();
                        comment.setId(commentNode.get("id").asLong());
                        comment.setPost_id(commentNode.get("post_id").asLong());
                        comment.setName(commentNode.get("name").asText());
                        comment.setEmail(commentNode.get("email").asText());
                        comment.setBody(commentNode.get("body").asText());
                                    
                        Comments.add(comment);
                    }
                }

                commentRepository.saveAll(Comments);
             
             
         } catch (HttpClientErrorException e) {
             //404 error 
             System.err.println("Error fetching comments for post " + ": " + e.getMessage());
         } catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    	
    	
    	
    }
}
