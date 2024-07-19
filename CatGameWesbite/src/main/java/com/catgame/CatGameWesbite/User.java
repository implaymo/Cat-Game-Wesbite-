package com.catgame.CatGameWesbite;


import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection; 
import java.util.List;
import jakarta.persistence.Column; 
import jakarta.persistence.Entity; 
import jakarta.persistence.Id; 
import jakarta.persistence.Table; 
import org.springframework.security.core.GrantedAuthority; 


@Entity
@Table(name = "users")
public class User implements UserDetails {

   private static final long serialVersionUID = 1L;

   @Id
   @Column(name = "id")
   private Long id;

   @Column(name = "email")
   private String email;

   @Column(name = "password")
   private String password;

   public User() {
   }

   public User(Long id, String email, String password) {
      this.id = id;
      this.email = email;
      this.password = password;
   }

   @Override
   public Collection<? extends GrantedAuthority> getAuthorities() {
      return List.of(() -> "ROLE_USER");
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

   public void setEmail(String email) {
      this.email = email;
   }

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   @Override
   public boolean isAccountNonExpired() {
      return true;
   }

   @Override
   public boolean isAccountNonLocked() {
      return true;
   }

   @Override
   public boolean isCredentialsNonExpired() {
      return true;
   }

   @Override
   public boolean isEnabled() {
      return true;
   }
}
