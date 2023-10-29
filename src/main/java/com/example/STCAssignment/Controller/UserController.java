package com.example.STCAssignment.Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import com.example.STCAssignment.Model.Comment;
import com.example.STCAssignment.Model.Post;
import com.example.STCAssignment.Model.User;
import com.example.STCAssignment.Model.UserInfoDTO;
import com.example.STCAssignment.Repository.CommentRepository;
import com.example.STCAssignment.Repository.PostRepository;
import com.example.STCAssignment.Repository.UserRepository;
import com.example.STCAssignment.Service.UserService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@RestController
public class UserController {
	
	    @Autowired
	    private UserService userService;

	    private static final String API_URL = "https://gorest.co.in/public/v2/users?page=10&per_page=100";
	    

	    @GetMapping("/allUsers")
	    public ResponseEntity<String> fetchAndStoreMaleUsers() {
	        RestTemplate restTemplate = new RestTemplate();
	        ResponseEntity<String> response = restTemplate.getForEntity(API_URL, String.class);

	        if (response.getStatusCode() == HttpStatus.OK) {
	            try {
	                ObjectMapper objectMapper = new ObjectMapper();
	                JsonNode root = objectMapper.readTree(response.getBody());
	                List<User> maleUsers = new ArrayList<>();

	                for (JsonNode userNode : root) {
	                    String gender = userNode.get("gender").asText();
	                    if ("male".equalsIgnoreCase(gender)) {
	                        User user = new User();
	                        user.setId(userNode.get("id").asLong());
	                        user.setname(userNode.get("name").asText());
	                        user.setEmail(userNode.get("email").asText());
	                        user.setStatus(userNode.get("status").asText());
	                        user.setGender(gender);              
	                        maleUsers.add(user);
	                    }
	                }

	                userService.saveMaleUsers(maleUsers);

	                return new ResponseEntity<>("Successfully saved male users to the database.", HttpStatus.OK);
	            } catch (IOException e) {
	                e.printStackTrace();
	                return new ResponseEntity<>("Failed to process the API response.", HttpStatus.INTERNAL_SERVER_ERROR);
	            }
	        } else {
	            return new ResponseEntity<>("Failed to fetch data from the API.", HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    }
	    
	    
	    
	    
	    //-------------------------------------------------------------------
	    @GetMapping("/user/{userId}")
	    public ResponseEntity<UserInfoDTO> getUserInfo(@PathVariable Long userId) {
	    	return userService.getUserInfo(userId);
	    }
	    
	    //---------------------------------------------------------------------------------------------
	    
	    @PostMapping("/updateUserInfo")
	    public void updateUserInfo(@RequestBody ObjectNode json) {
	    	 Long userId = json.get("userId").asLong();
	    	 String updatedName = json.get("updatedName").asText();
	    	 
	    	 userService.updateUserInfo(userId, updatedName);
	    }
	    
	    

}
