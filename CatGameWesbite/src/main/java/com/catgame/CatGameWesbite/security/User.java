package com.catgame.CatGameWesbite.security;


import java.util.Collection; 
import java.util.List;
import jakarta.persistence.Entity; 
import jakarta.persistence.Id; 
import jakarta.persistence.Table; 
import org.springframework.security.core.GrantedAuthority; 
import org.springframework.security.core.userdetails.UserDetails; 

@Entity 
@Table(name = "users") 
public class User implements UserDetails { 

   /** 
   * 
   */ 
   private static final long serialVersionUID = 1L;

   @Id 
   private String email; 
   private String password;

   public User(String email, String password, boolean accountNonLocked) { 
      this.email = email; 
      this.password = password; 
   } 
   @Override 
   public Collection<? extends GrantedAuthority> getAuthorities() { 
      return List.of(); 
   }
   @Override
   public String getPassword() {    
      return password; 
   } 
   public void setPassword(String password) { 
      this.password = password; 
   } 
   @Override 
   public String getUsername() { 
      return email; 
   } 
   public void setUsername(String email) { 
      this.email = email; 
   } 
   @Override 
   public boolean isAccountNonExpired() { 
      return true; 
   } 
   @Override public boolean isCredentialsNonExpired() { 
      return true; 
   } 
   @Override public boolean isEnabled() { 
   return true; 
   } 
}