package com.zags.gorodovoy.repository;

import com.zags.gorodovoy.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;


public interface UserRepository extends CrudRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE CONCAT(u.id, u.firstName, u.middleName, u.lastName, u.birthDate, u.passportNumber, u.passportSeries,  u.gender) LIKE %?1%")
    public Iterable<User> search(String keyword);

    List<User> findAll();

    User findByUsername(String username) throws UsernameNotFoundException;


}
