package com.sts;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Book {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String title;
	@OneToOne(cascade = CascadeType.ALL) //mapping implementationin spring boot of author user created datatype, one to one means one book has one author
	@JsonManagedReference //this makes spring consider this as a starting point to fetch data for author else spring would fetch author and author would fetch book and this wouldkeep on going, this acts as a parent, do refer Author class for another tag
	private Author author; //cascade is used to fulfill all the dependencies or process all the related data, author in this case, before any operation on book class. For instance if a book is deleted then related author will also get deleted
	public Book() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Book(int id, String title, Author author) {
		super();
		this.id = id;
		this.title = title;
		this.author = author;
	}
	@Override
	public String toString() {
		return "Book [id=" + id + ", title=" + title + ", author=" + author + "]";
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Author getAuthor() {
		return author;
	}
	public void setAuthor(Author author) {
		this.author = author;
	}
	
	
}


/*
//way to send nested JSON data
{
"id":13,
"title": "SJ2",
"author": {
        "id":14,
        "name":"PJ2"
    }
}
*/