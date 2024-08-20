package com.student.repository;

import com.student.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUsername(String username);

    @Query("select (count(u) > 0) from User u where u.username = ?1")
    boolean existsByUsername(String username);
}
