package com.sts;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

//entity class requires a repository/interface for data storage, they can be either implemented from crudrepository or jparepository interface
public interface UserRepository extends CrudRepository<User, Integer> { //crud repo gives basic crud functionality, use jparepository for additional features
	public List<User> findByName(String name); //this method alone will tell spring to return data from DB by name, no need to define the method, in this "find" is introducer and "byName" is criteria
	//these above methods work on jpql
	
	@Query("select u from User u") //this is way to fire jpql/hibernate queries without any definition, remember to use the table name same as entity class defined in spring boot
	public List<User> getAllUser();
	
	@Query("select u from User u where u.name=:n")
	public List<User> getUserByName(@Param("n") String name); //param annotation is a way to bind name var with n in query
	
	@Query(value = "select * from user", nativeQuery = true) //way to fire native query
	public List<User> getUser();

}
