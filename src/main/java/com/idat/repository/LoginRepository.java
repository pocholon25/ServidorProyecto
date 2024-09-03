package com.idat.repository;

import com.idat.model.LoginRequest;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginRepository extends JpaRepository<LoginRequest, Long> {
	Optional<LoginRequest> findByEmail(String email);
	
}
