package com.example.STCAssignment.Controller;

import com.example.STCAssignment.Service.PostService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@RestController
public class PostController {
	
	
	 private final PostService postService;

	 @Autowired
     public PostController(PostService postService) {
		 this.postService = postService;
	 }
	 
	 
	 @GetMapping("/getUsersPosts")
	    public ResponseEntity<String> getUsersPosts() {
	        postService.getUsersPosts();
	        return new ResponseEntity<>("Successfully saved users' posts to the database.", HttpStatus.OK);
	    }
	    
	 
	 
	    //-----------------------------------------------------------------------
	 
	 
	private static final String API_URL = "https://gorest.co.in/public/v1/posts?page=1&per_page=100";
	
	@GetMapping("/allPosts")
    public ResponseEntity<List<JsonNode>> getAllPosts() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(API_URL, String.class);
        
        
        if (response.getStatusCode() == HttpStatus.OK) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode root = objectMapper.readTree(response.getBody());
                List<JsonNode> posts = new ArrayList<>();

                
                
                for (JsonNode post : root.get("data")) {
                    posts.add(post);
                }

                // Sort 
                posts.sort(Comparator.comparing(post -> post.get("id").asLong()));

                return new ResponseEntity<>(posts, HttpStatus.OK);
            } catch (IOException e) {
                e.printStackTrace();
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        
        
        

       
    }
	


}
