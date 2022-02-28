package com.sts;

import java.util.Optional;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class JpademoApplication { //do refer application.properties for db configuration

	public static void main(String[] args) {
		
		ApplicationContext context=SpringApplication.run(JpademoApplication.class, args);
		UserRepository userRepository=context.getBean(UserRepository.class);
		User user=new User("SJ", "PD");
		
		//userRepository.save(user); //notice in userrepository class how only interface has been implemented and no other coding , that is beauty of spring boot
		
		/*
		Optional<User> optional=userRepository.findById(11); //way to get data from DB by id
		user=optional.get();
		System.out.println(user);
		*/
		
		/*
		Iterable<User> itr=userRepository.findAll(); //way to get all data from db and print
		itr.forEach(user1->{System.out.println(user1);});
		*/
		
		//userRepository.delete(user); //deletes entity recieved from DB above
		
		//System.out.println(userRepository.findByName("SJ")); //demo of custom finder methods, refer to query creation heading in documentation to create more custom methods, findByNameContaining etc.
		
		System.out.println(userRepository.getAllUser()); //demo of firing native/jpql/hibernate
	}

}
