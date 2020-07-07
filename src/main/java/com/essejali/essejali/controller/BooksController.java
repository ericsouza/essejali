package com.essejali.essejali.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import com.essejali.essejali.model.Book;
import com.essejali.essejali.repository.BookRepository;

@Controller
@RequestMapping("books")
public class BooksController {
	@Autowired
	private BookRepository bookRepository;
	
	@GetMapping
    public String index(Model model){
		List<Book> books = bookRepository.findAll(); 
		model.addAttribute("books", books);
		System.out.println();
        return "books/index";
    }
	
	@GetMapping("/{id}")
	public String book(@PathVariable Long id, Model model) {
		Optional<Book> optional = bookRepository.findById(id);
		if(optional.isPresent()) {
			model.addAttribute(optional.get());
			return "books/book";
		}
		
		return "404";
	}
	

}
