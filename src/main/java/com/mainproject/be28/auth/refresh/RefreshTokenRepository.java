package com.mainproject.be28.auth.refresh;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Long> {

    RefreshToken findByUsername(String email);

    RefreshToken findByJws(String jws);
}