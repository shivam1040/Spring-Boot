package com.sts;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController //generally rest controller is used for api handling controller classes
public class Controller {
	
	@Autowired //refer to bookservice classfor more details
	private BookService bookService;
	
	//@RequestMapping(value = "/get", method = RequestMethod.GET)
	@GetMapping("/get") //same as above but need not to specify method attribute explicityly
	//@ResponseBody when restcontroller is used response body is not required
	public List<Book> get() {
		//Book book=new Book(1, "SJ", "PJ");
		//return book; //this is returned in JSON format to view and this is done automatically by spring boot and jackson api
		
		return this.bookService.getAll();
	}
	
	@GetMapping("/get/{id}") //get book by id
	public Book getByID(@PathVariable("id") int id) { //we can use response entity here to send response codes by check recieved obj is null or not
		return this.bookService.getBookByID(id);
	}
	
	@PostMapping("/post") //create a book by post method
	public Book post(@RequestBody Book b) { //request body would automatically map recieved data to variables of book and call the post function
		this.bookService.post(b); //remember to post the data in json format and rest mapping would be done by jackson
		return b;
	}
	
	@DeleteMapping("/book/{id}") //delete book by id
	public void delete(@PathVariable("id") int id) {
		this.bookService.delete(id);
	}
	
	@PutMapping("/put/{id}") //update book by id
	public Book update(@PathVariable("id") int id, @RequestBody Book book) {
		return this.bookService.put(id, book);
	}
	
		@GetMapping("/gets") //same as above but need not to specify method attribute explicityly
		public ResponseEntity<List<Book>> gets() { //demo of ResponseEntity, it is used to send  response codes as per our logic
				List<Book> book=this.bookService.getAll();
				if(book.size()<2)
					return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
				return ResponseEntity.of(Optional.of(book));
		}
		
		@GetMapping("/getDAO") //data get from DB
		public List<Book> getDAO(){
			return this.bookService.getAllDAO();
		}
		
		@PostMapping("/postDAO") //create a book by post method, and saving into DB
		public Book postDAO(@RequestBody Book b) { //request body would automatically map recieved data to variables of book and call the post function
			this.bookService.postDAO(b); //remember to post the data in json format and rest mapping would be done by jackson
			return b;
		}
}