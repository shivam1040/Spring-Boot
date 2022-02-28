package com.sts;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service //this tag is necessary to tell the spring boot autowiring annotaion should create obj for this class, Component annotation can be used too
public class BookService { //fake service class for api functions
	private static List<Book> list1=new ArrayList<Book>();
	
	static {
		list1.add(new Book(2, "SJ1", new Author("PJ")));
	}
	
	public List<Book> getAll(){
		return list1;
	}
	
	public Book getBookByID(int id) {
		Book book=null;
		try {
			book= list1.stream().filter(e->e.getId()==id).findFirst().get();
		} catch (Exception e2) {
			// TODO: handle exception
		}
		return book;
	}
	
	public void post(Book b) {
		list1.add(b);
	}

	public void delete(int id) {
		list1=list1.stream().filter(book->book.getId()!=id).collect(Collectors.toList());
	}

	public Book put(int id, Book book) { //update by id
		Book b=this.getBookByID(id);
		int index=list1.indexOf(b);
		list1.set(index, book);
		return list1.get(index);
	}
	
	//below is the implementtation for DAO functionality, above is a fakeservice class
	@Autowired
	private BookRepository bookRepository;
	
	public List<Book> getAllDAO(){
		return (List<Book>) this.bookRepository.findAll(); //since findall return iterable and we know that iterable is parent class of list so this retun can be typecasted into list
	}
	
	public Book getBookByIDDAO(int id) {
		return this.bookRepository.findById(id);
	}
	
	public void postDAO(Book b) {
		this.bookRepository.save(b);
	}
	
	public void deleteDAO(int id) {
		this.bookRepository.deleteById(id);
	}
	
	public void putDAO(int id, Book book) { //update by id
		this.bookRepository.save(book); //save works for both create and update
	}
}