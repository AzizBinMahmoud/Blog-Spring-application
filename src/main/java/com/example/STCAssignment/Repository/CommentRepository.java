package com.example.STCAssignment.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.STCAssignment.Model.Comment;
import com.example.STCAssignment.Model.Post;

public interface CommentRepository extends JpaRepository<Comment, Long> {
	
	@Query(value = "SELECT * FROM Comment c WHERE c.post_id IN (SELECT p.id FROM Post p WHERE p.user_id = :userId)", nativeQuery = true)
    List<Comment> findCommentsForUserPosts(@Param("userId") Long userId);


}
