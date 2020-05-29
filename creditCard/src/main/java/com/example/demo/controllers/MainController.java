package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.CreditCard;
import com.example.demo.CreditCardDao;
import com.example.demo.User;
import com.example.demo.UserDao;
//import org.springframework.web.bind.annotation.RestController;

@Controller
public class MainController {
	
	@Autowired
	private UserDao dao;
	
	@Autowired
	private CreditCardDao cdao;
	
	
	@GetMapping("/")
	public String homePage(Model model) {
		Iterable<User> users = dao.findAll();
		model.addAttribute("users", users);
		return "index";
	}
	
	@GetMapping("/accountinfo/{userid}")
	public String getAccountInfo(@PathVariable int userid, Model model) {
		User currentUser = dao.findById((Integer)userid).get();
		CreditCard currentCard = cdao.findByCardNum(currentUser.getCardNum());
		model.addAttribute("user", currentUser);
		model.addAttribute("card", currentCard);
		return "accountinfo";
	}
	
}
