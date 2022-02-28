package com.sts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository //generally we use such annotations to define type of class, Service tag for service classes, Component tag can be used in general and so on
public class Dao {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	public Dao() {
		
	}
	
public int createTable() { //if this query is run in constructor then the prgrm would throw error since update will be run without instantiation jdbctempelate so nullexception error
	String query="CREATE TABLE IF NOT EXISTS Users(id int primary key, name varchar(200), age int, city varchar(100))";
	return this.jdbcTemplate.update(query);
	}

public int insert(int id, String name, int age, String city) {
	String query="INSERT INTO Users(id, name, age, city) values(?, ?, ?, ?)";
	return this.jdbcTemplate.update(query, new Object[] {id, name, age, city});
}
}