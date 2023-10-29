package com.example.STCAssignment.Model;

import jakarta.persistence.*;

@Entity
public class Post {
	
	@Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long user_id; 
    private String title;
    private String body;
	
    
    public Post() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Post(Long id, Long userId, String title, String body) {
		super();
		this.id = id;
		this.user_id = userId;
		this.title = title;
		this.body = body;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Long getUserId() {
		return user_id;
	}


	public void setUserId(Long userId) {
		this.user_id = userId;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getBody() {
		return body;
	}


	public void setBody(String body) {
		this.body = body;
	}
    
    
    

}
