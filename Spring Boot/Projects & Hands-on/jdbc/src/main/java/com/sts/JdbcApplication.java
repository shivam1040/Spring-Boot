package com.sts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JdbcApplication implements CommandLineRunner{ //since we can't use instance vars/methods in static methods(main method in this case) so the workaround is to implement CmdLineRunner and run such instructions in unimplemented method of interface
	@Autowired
	private Dao dao;
	public static void main(String[] args) {
		SpringApplication.run(JdbcApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		this.dao.createTable();
		this.dao.insert(1, "SJ", 24, "PDN");
	}
}
