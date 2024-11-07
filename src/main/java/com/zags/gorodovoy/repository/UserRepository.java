package com.zags.gorodovoy.repository;

import com.zags.gorodovoy.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.Query;


public interface UserRepository extends CrudRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE CONCAT(u.id, u.firstName, u.middleName, u.lastName, u.birthDate, u.passportNumber, u.passportSeries, u.documents, u.gender, u.requests, u.role) LIKE %?1%")
    public Iterable<User> search(String keyword);
}