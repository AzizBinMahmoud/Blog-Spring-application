package com.example.STCAssignment.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "user")
public class User {
	

	    @Id
	    //@GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	    private String name;
	    private String email;
	    private String gender;
	    private String status;


		public User(Long id, String name, String email, String gender, String status) {
			super();
			this.id = id;
			this.name = name;
			this.email = email;
			this.gender = gender;
			this.status = status;
		}
		
		public User() {
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getname() {
			return name;
		}

		public void setname(String name) {
			this.name = name;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getGender() {
			return gender;
		}

		public void setGender(String gender) {
			this.gender = gender;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}
		
		
	    
	    
	

}
