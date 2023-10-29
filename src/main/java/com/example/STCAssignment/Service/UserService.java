package com.example.STCAssignment.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.STCAssignment.Model.Comment;
import com.example.STCAssignment.Model.Post;
import com.example.STCAssignment.Model.User;
import com.example.STCAssignment.Model.UserInfoDTO;
import com.example.STCAssignment.Repository.CommentRepository;
import com.example.STCAssignment.Repository.PostRepository;
import com.example.STCAssignment.Repository.UserRepository;

@Service
public class UserService {
	
//	@Autowired
//    private UserRepository userRepository;
	
	private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    
    @Autowired
    public UserService(UserRepository userRepository, PostRepository postRepository, CommentRepository commentRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }
	

    public void saveMaleUsers(List<User> maleUsers) {
        userRepository.saveAll(maleUsers);
        
    }
    
    
    //-----------------------------------------------------------------------------------------------
    
    
    public ResponseEntity<String> updateUserInfo(long userId, String updatedName) {
    	
    	 Optional<User> userOptional = userRepository.findById(userId);
    	 if (userOptional.isPresent()) {
             User user = userOptional.get();
             user.setname(updatedName);
             userRepository.save(user);
             return new ResponseEntity<>("User information updated successfully", HttpStatus.OK);
         } else {
             return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
         }
    	
    }
    
    //---------------------------------------------------------------------------------------------------
    
    public ResponseEntity<UserInfoDTO> getUserInfo(@PathVariable Long userId) {
        
        Optional<User> user = userRepository.findById(userId);
        
        if (user.isPresent()) {
            
            UserInfoDTO userInfoDTO = new UserInfoDTO();
            userInfoDTO.setUser(user.get());

            
            List<Post> userPosts = postRepository.findByUserId(userId);
            userInfoDTO.setUserPosts(userPosts);

            
            List<Comment> postComments = commentRepository.findCommentsForUserPosts(userId);
            userInfoDTO.setPostComments(postComments);

            
            return new ResponseEntity<>(userInfoDTO, HttpStatus.OK);
        } else {
           
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    /**/

}
