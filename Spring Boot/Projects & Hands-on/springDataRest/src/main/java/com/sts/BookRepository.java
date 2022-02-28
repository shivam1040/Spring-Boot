package com.sts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "book", collectionResourceRel = "bo") //this will change api calling name from bookses to book, localhost:8080/book, collectionresourcserel param change var name defined in recieved json response to boo
public interface BookRepository extends JpaRepository<Books, Integer> { //when this repository gets created all the general apiS get created automatically without having need to create controller or service class
	//making a post request to api in json format would create/update data in DB
	//localhost:8080/bookses, will give all books for get
	//localhost:8080/bookses/3, will update id 3 with post data
	
}
