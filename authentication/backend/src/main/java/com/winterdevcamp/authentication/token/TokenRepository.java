package com.winterdevcamp.authentication.token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<TokenEntity, Long> {

    @Query("select distinct t from Token t join fetch t.user where t.user.userId=:userId")
    Optional<TokenEntity> findByUserId(@Param("userId") Long userId);
}
