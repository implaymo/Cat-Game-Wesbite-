package com.catgame.CatGameWesbite.models;

import jakarta.persistence.*;

@Entity 
@Table(name = "users") 
public class LoginUser { 


   @Id 
   @GeneratedValue(strategy=GenerationType.IDENTITY)
   private int id;

   private String name;

   @Column(unique = true, nullable = false)
   private String email;

   private String password;
   private int highscore;



    // Getters
    public int getId() {
      return id;
  }

  public String getName() {
      return name;
  }

  public String getEmail() {
      return email;
  }

  public String getPassword() {
      return password;
  }

  public int getHighscore() {
      return highscore;
  }




  // Setters
  public void setId(int id) {
      this.id = id;
  }

  public void setName(String name) {
      this.name = name;
  }

  public void setEmail(String email) {
      this.email = email;
  }

  public void setPassword(String password) {
      this.password = password;
  }

  public void setHighscore(int highscore) {
      this.highscore = highscore;
  }


}