package com.codefellows.CodeFellowship.infrastructure;

import com.codefellows.CodeFellowship.domain.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApplicationUserRepository extends JpaRepository<ApplicationUser,Long> {

    Optional<ApplicationUser> findApplicationUserByUsername(String username);
}
