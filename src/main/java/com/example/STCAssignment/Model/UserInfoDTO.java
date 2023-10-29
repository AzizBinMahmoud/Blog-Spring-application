package com.example.STCAssignment.Model;

import java.util.List;

public class UserInfoDTO {
	
	private User user;
    private List<Post> userPosts;
    private List<Comment> postComments;
	
    
    public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public List<Post> getUserPosts() {
		return userPosts;
	}
	public void setUserPosts(List<Post> userPosts) {
		this.userPosts = userPosts;
	}
	public List<Comment> getPostComments() {
		return postComments;
	}
	public void setPostComments(List<Comment> postComments) {
		this.postComments = postComments;
	}
    
    

}
