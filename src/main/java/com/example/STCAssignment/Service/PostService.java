package com.example.STCAssignment.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.example.STCAssignment.Model.Post;
import com.example.STCAssignment.Model.User;
import com.example.STCAssignment.Repository.PostRepository;
import com.example.STCAssignment.Repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class PostService {
    private static final String EXTERNAL_API_URL = "https://gorest.co.in/public/v2/posts?page=6&per_page=100"; // Replace with the actual API URL
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final RestTemplate restTemplate;
    

	@Autowired
    public PostService(UserRepository userRepository, PostRepository postRepository, RestTemplate restTemplate) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.restTemplate = restTemplate;
    }

    public void getUsersPosts() {
//        
	        ResponseEntity<String> response = restTemplate.getForEntity(EXTERNAL_API_URL, String.class);

	        if (response.getStatusCode() == HttpStatus.OK) {
	            try {
	                ObjectMapper objectMapper = new ObjectMapper();
	                JsonNode root = objectMapper.readTree(response.getBody());
	                List<Post> usersPosts = new ArrayList<>();

	                for (JsonNode postNode : root) {
	                    
	                    if (userRepository.existsById(postNode.get("user_id").asLong())) {
	                    
	                        Post post = new Post();
	                        post.setId(postNode.get("id").asLong());
	                        post.setUserId(postNode.get("user_id").asLong());
	                        post.setTitle(postNode.get("title").asText());
	                        post.setBody(postNode.get("body").asText());
	                                    
	                        usersPosts.add(post);
	                    }
	                }
	                
	                postRepository.saveAll(usersPosts);
                       
               
             } catch (HttpClientErrorException e) {
                 // 404 error 
                 System.err.println("Error fetching posts for user " + ": " + e.getMessage());
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
