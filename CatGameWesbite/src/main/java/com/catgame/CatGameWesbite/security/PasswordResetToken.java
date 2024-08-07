package com.catgame.CatGameWesbite.security;

import com.catgame.CatGameWesbite.models.LoginUser;
import com.catgame.CatGameWesbite.repository.UserRepository;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Id;


@Entity
public class PasswordResetToken {

    private static final int EXPIRATION = 60 * 24;
 
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
 
    private String token;
 
    @OneToOne(targetEntity = LoginUser.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "id")
    private LoginUser user;
 
    private Date expiryDate;
}