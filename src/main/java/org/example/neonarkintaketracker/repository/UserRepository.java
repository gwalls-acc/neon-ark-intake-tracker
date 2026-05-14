package org.example.neonarkintaketracker.repository;
import org.example.neonarkintaketracker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    // Using JOIN FETCH prevents the "N+1" problem where the app makes
    // a separate database call for every user's roles.
    @Query("SELECT DISTINCT u FROM User u LEFT JOIN FETCH u.roles")
    List<User> findAllWithRoles();
}