package com.catgame.CatGameWesbite.repository;

import org.springframework.data.jpa.repository.JpaRepository; 

import com.catgame.CatGameWesbite.models.LoginUser; 

public interface UserRepository extends JpaRepository<LoginUser, String> { 
   public LoginUser findUserByEmail(String email); 
}