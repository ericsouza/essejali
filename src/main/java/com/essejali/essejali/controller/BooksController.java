package com.essejali.essejali.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.essejali.essejali.model.Book;
import com.essejali.essejali.model.User;
import com.essejali.essejali.repository.BookRepository;
import com.essejali.essejali.repository.UserRepository;

@Controller
@RequestMapping("books")
public class BooksController {
	@Autowired
	private BookRepository bookRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping
    public String index(Model model){

		List<Book> books = bookRepository.findAll(); 
		model.addAttribute("books", books);
		
        return "books/index";
    }
	
	@GetMapping("/{id}")
	public String book(@PathVariable Long id, Model model, Authentication auth) {
		Optional<Book> optional = bookRepository.findById(id);
		if(optional.isPresent()) {
			
			User userPrincipal = (User) auth.getPrincipal();
			Long userId = userPrincipal.getId();
			Optional<User> user = userRepository.findById(userId);
			
			int isRead = 0;
			
			if (user.get().getBooks().contains(optional.get())) {
				isRead = 1;
			}
			
			model.addAttribute(optional.get());
			model.addAttribute("isRead", isRead);
			return "books/book";
		}
		
		return "404";
	}
	

}
