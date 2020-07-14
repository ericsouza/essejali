package com.essejali.essejali.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import javax.transaction.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.essejali.essejali.model.Book;
import com.essejali.essejali.model.User;
import com.essejali.essejali.repository.UserRepository;

@Controller
@RequestMapping("profile")

public class ProfileController {
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("index")
	@Transactional
    public String index(Model model, Authentication auth){
		User userPrincipal = (User) auth.getPrincipal();
		Long userId = userPrincipal.getId();
		Optional<User> user = userRepository.findById(userId);
		
		List<User> users = userRepository.findByOrderByPointsDesc();
		int rankingPosition = users.indexOf(user.get()) + 1;
		
		
		Set<Book> books = user.get().getBooks();
		
		Long paginometro = 0L;
		
		for (Book book: books) {
			paginometro += book.getPages();
		}
		
		
		List<String> trophies = new ArrayList<String>();
		if (user.get().getTrophies().length() > 1) {
			trophies = Arrays.asList(user.get().getTrophies().split(","));
		}
		
		
		
		model.addAttribute("user", user.get());
		model.addAttribute("books", books);
		model.addAttribute("trophies", trophies);
		model.addAttribute("paginometro", paginometro);
		model.addAttribute("rankingPosition", rankingPosition);
		
        return "profile/index";
    }
}
