package com.essejali.essejali.controller;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
    public ResponseEntity<?> index(@RequestParam Long userId, @RequestParam Long bookId, @RequestParam int unread){
		
		System.out.println("bookId: " + bookId + "  userId: " + userId);
		
		Optional<User> user = userRepository.findById(userId);
		
		Optional<Book> book = bookRepository.findById(bookId);
		
		
		if (book.isPresent() && user.isPresent()) {
			
			Set<Book> books = user.get().getBooks();
			BookStyle bookStyle = book.get().getStyle();
			int countSameStyle = 0;
			for(Book b: books) {
				if (b.getStyle().equals(bookStyle)) {
					countSameStyle++;
				}
			}
			
			int gainPoints = 0;
			
			if (unread == 1) {
				if (countSameStyle == 5 ){
					
					String oldTrophies = user.get().getTrophies();
					List<String> trophiesList = new LinkedList<String>(Arrays.asList(oldTrophies.split(",")));
					trophiesList.remove(bookStyle.toString());
					
					String newTrophies = String.join(",", trophiesList);
					
					user.get().setTrophies(newTrophies);
				}
				
				
				if (!(user.get().getBooks().contains(book.get()))) {
					return ResponseEntity.notFound().build();
				}
				
				Set<Book> newBooks = user.get().getBooks();
				newBooks.remove(book.get());
				
				user.get().setBooks(newBooks);
				gainPoints = (-1)*(Math.round(book.get().getPages() / 100) + 1);
			} else {
				
				if(user.get().getBooks().contains(book.get())) {
					return ResponseEntity.ok().build();
				}
				
				if (countSameStyle == 4 ){
					String oldTrophies = user.get().getTrophies();
					String newTrophies;
					if (oldTrophies.length() > 1) {
						newTrophies = oldTrophies + "," + bookStyle;
					} else {
						newTrophies = "" + bookStyle;
					}
					user.get().setTrophies(newTrophies);
				}
				
				Set<Book> newBooks = user.get().getBooks();
				newBooks.add(book.get());
				
				user.get().setBooks(newBooks);
				
				gainPoints = (Math.round(book.get().getPages() / 100) + 1);
			}
			
			
			user.get().setPoints(user.get().getPoints() + gainPoints); 
		
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
			
			Set<Book> books = user.get().getBooks();
			
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