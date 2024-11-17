package com.zags.gorodovoy.repository;
import com.zags.gorodovoy.models.Role;

import com.zags.gorodovoy.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {
    @Query("SELECT r FROM Role r")
    public List<Role> findAllRoles();
    Role findByName(String name);
}