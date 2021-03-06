package com.sts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Integer> {
	@Query("select u from User u where email=:email")
	public User getUserByUsername(@Param("email") String email); //intila stepp: user returning config for spring security
}
