package com.nexus.nexusproject.Repository;
// path of the directory
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nexus.nexusproject.model.User;

@Repository // tells spring that this interface is a repository
public interface UserRepository extends JpaRepository<User, Long> {
    // extends JPA repository mtb basic CRUD operations automatically add ho jata h
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}