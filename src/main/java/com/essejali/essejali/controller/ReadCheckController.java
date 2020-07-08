package com.essejali.essejali.controller;



import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.essejali.essejali.model.Book;
import com.essejali.essejali.model.BookStyle;
import com.essejali.essejali.model.User;
import com.essejali.essejali.repository.BookRepository;
import com.essejali.essejali.repository.UserRepository;



@Controller
@RequestMapping("read-check")
public class ReadCheckController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BookRepository bookRepository;
	
	@GetMapping
	@Transactional
	@ResponseBody
    public ResponseEntity<?> index(@RequestParam Long userId, @RequestParam Long bookId){
		
		System.out.println("bookId: " + userId + "  userId: " + bookId);
		
		Optional<User> user = userRepository.findById(userId);
		
		Optional<Book> book = bookRepository.findById(bookId);
		
		
		if (book.isPresent() && user.isPresent()) {
			
			if(user.get().getBooks().contains(book.get())) {
				return ResponseEntity.ok().build();
			}
			
			user.get().getBooks().add(book.get());
			
			int gainPoints = (Math.round(book.get().getPages() / 100) + 1);
			
			user.get().setPoints(user.get().getPoints() + gainPoints); 
			
			List<Book> books = user.get().getBooks();
			
			BookStyle bookStyle = book.get().getStyle();
			
			int countSameStyle = 0;
			for(Book b: books) {
				if (b.getStyle().equals(bookStyle)) {
					countSameStyle++;
				}
			}
			
			if (countSameStyle == 5 ){
				String oldTrophies = user.get().getTrophies();
				String newTrophies;
				if (oldTrophies.length() > 1) {
					newTrophies = oldTrophies + "," + bookStyle;
				} else {
					newTrophies = "" + bookStyle;
				}
				user.get().setTrophies(newTrophies);
			}
			
			userRepository.save(user.get());
			
			return ResponseEntity.ok().build();
		}
		
		return ResponseEntity.notFound().build();
    }
	
	@GetMapping("/check")
	@ResponseBody
    public ResponseEntity<?> check(@RequestParam Long userId){
		
		Optional<User> user = userRepository.findById(userId);
		
		if (user.isPresent()) {
			
			List<Book> books = user.get().getBooks();
			
			System.out.println("vou mostrar os books");
			for(Book b : books) {
			    System.out.println(b.getTitle());
			}
			
			System.out.println(user.get().getPoints());
			System.out.println(user.get().getTrophies());
			
			return ResponseEntity.ok().build();
			
		}
		
		return ResponseEntity.notFound().build();
    }
	

}