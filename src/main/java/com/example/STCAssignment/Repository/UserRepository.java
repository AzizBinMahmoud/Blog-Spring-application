package com.example.STCAssignment.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.STCAssignment.Model.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
