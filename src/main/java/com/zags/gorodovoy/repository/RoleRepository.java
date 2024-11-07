package com.zags.gorodovoy.repository;
import com.zags.gorodovoy.models.Role;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {

    @Query("SELECT u FROM Role u WHERE CONCAT(u.id, u.roleName) LIKE %?1%")
    public Iterable<Role> search(String keyword);

    @Query("SELECT r FROM Role r")
    public List<Role> findAllRoles();
}
