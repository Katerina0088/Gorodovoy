package com.zags.gorodovoy.repository;


import com.zags.gorodovoy.models.User;
import org.antlr.v4.runtime.misc.LogManager;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;



import com.zags.gorodovoy.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.id > :idMin")
    List<User> findUsersGreaterThanId(@Param("idMin") Long idMin);

    @Query("SELECT u FROM User u WHERE u.email = ?1")
    public UserDetails findUserByEmail(String email) throws UsernameNotFoundException;

    @Query("SELECT u FROM User u WHERE u.id = :id")
    public User findUserById(@Param("id") Long id);

    List<User> findAll();

    @Query("SELECT u FROM User u WHERE LOWER(u.email) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(u.firstName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(u.lastName) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<User> search(@Param("keyword") String keyword);


    }
