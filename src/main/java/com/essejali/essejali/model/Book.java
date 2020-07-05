package com.essejali.essejali.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
public class Book {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String title;
	private String bookAuthor;
	private int pages;
	
	@Column(length = 4095)
	private String description;
	private String imageFilename;
	
	@Enumerated(EnumType.STRING)
	private BookStyle style;
	
	public Book() {
	}
	
	public Book(String title, String bookAuthor, int pages, String description, String imageFilename, BookStyle style) {
		this.title = title;
		this.bookAuthor = bookAuthor;
		this.pages = pages;
		this.description = description;
		this.imageFilename = imageFilename;
		this.style = style;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBookAuthor() {
		return bookAuthor;
	}

	public void setBookAuthor(String bookAuthor) {
		this.bookAuthor = bookAuthor;
	}

	public int getPages() {
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImageFilename() {
		return imageFilename;
	}

	public void setImageFilename(String imageFileName) {
		this.imageFilename = imageFileName;
	}

	public BookStyle getStyle() {
		return style;
	}

	public void setStyle(BookStyle style) {
		this.style = style;
	}
	
	
}
