package com.zags.gorodovoy.repository;
import com.zags.gorodovoy.models.Role;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.Query;

public interface RoleRepository extends CrudRepository<Role, Long> {
    @Query("SELECT u FROM Role u WHERE CONCAT(u.id, u.roleName) LIKE %?1%")
    public Iterable<Role> search(String keyword);}
