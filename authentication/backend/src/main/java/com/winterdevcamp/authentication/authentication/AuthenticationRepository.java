package com.winterdevcamp.authentication.authentication;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthenticationRepository extends JpaRepository<AuthenticationEntity, Long> {
}
