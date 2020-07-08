package com.essejali.essejali.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.essejali.essejali.model.User;
import com.essejali.essejali.repository.UserRepository;

@Controller
@RequestMapping("ranking")
public class RankingController {
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("index")
    public String index(Model model){
		List<User> users = userRepository.findByOrderByPointsDesc();
		
		model.addAttribute("users", users);
        return "ranking/index";
    }
}
