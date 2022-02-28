package com.sts;



import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ContactRepository extends JpaRepository<Contact, Integer> {
	@Query("from Contact as d where d.users.id=:userId")
	public Page<Contact> findContactsByUser(@Param("userId") int userId, Pageable pageable);
	public List<Contact> findByNameContainingAndUsers(String name, User user); //find record by name in contacts for particular user
}
