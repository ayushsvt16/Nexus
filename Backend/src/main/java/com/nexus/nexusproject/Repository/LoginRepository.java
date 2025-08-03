package com.nexus.nexusproject.Repository;

import com.nexus.nexusproject.model.LoginUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginRepository extends JpaRepository<LoginUser, Long> {
    // Custom method to find a user by their username
    LoginUser findByUsername(String username);
}
