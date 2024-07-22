package com.catgame.CatGameWesbite.security;

import java.util.Optional; 
import org.springframework.data.jpa.repository.JpaRepository; 
import org.springframework.stereotype.Repository; 

@Repository public interface UserRepository extends JpaRepository<User, String> { 
   Optional<User> findUserByEmail(String email); 
}