package com.sts;

import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book, Integer> { //here Integer is the class type of id variable defined in book class
	public Book findById(int id);
}
