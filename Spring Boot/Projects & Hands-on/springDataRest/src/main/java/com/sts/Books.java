package com.sts;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Books {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String title;
	private String Content;
	private String author;
	private int pages;
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
	public String getContent() {
		return Content;
	}
	public void setContent(String content) {
		Content = content;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public int getPages() {
		return pages;
	}
	public void setPages(int pages) {
		this.pages = pages;
	}
	@Override
	public String toString() {
		return "Books [id=" + id + ", title=" + title + ", Content=" + Content + ", author=" + author + ", pages="
				+ pages + "]";
	}
	public Books(String title, String content, String author, int pages) {
		super();
		this.title = title;
		Content = content;
		this.author = author;
		this.pages = pages;
	}
	public Books() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
