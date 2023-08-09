package com.daylog.repository;

import com.daylog.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findUserByEmailAndPassword(String email, String password);
    Optional<User> findByEmail(String email);
}
