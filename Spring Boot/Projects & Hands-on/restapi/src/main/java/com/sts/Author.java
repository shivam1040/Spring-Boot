package com.sts;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Author {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String name;
	@OneToOne(mappedBy = "author") //way to implement bidirectional one to one mapping that is every author has one book and vice versa, to avoid multiple FK creation in table, mapping is done by only one var that is author of book class
	@JsonBackReference //this tells boot that data should not be fetched from here to book to avoid infinite data fecthing, this act as a child
	private Book book;
	public Author(String name) {
		super();
		this.name = name;
	}
	public Author() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return "Author [id=" + id + ", name=" + name + "]";
	}
	 
	 
}
