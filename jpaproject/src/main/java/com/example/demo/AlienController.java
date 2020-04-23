package com.example.demo;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;



import net.bytebuddy.dynamic.DynamicType.Builder.FieldDefinition.Optional;

@Controller
public class AlienController {
	@Autowired
	AlienRepo arep;

	@RequestMapping("/")
	public String home() {
		return "home.jsp";
	}
	
//	@RequestMapping("/addAlien")
//	public String addAlien(Alien alien) {
//		arep.save(alien);
//		return "home.jsp";
//	}
	@RequestMapping("/aliens")
	@ResponseBody
	public List<Alien> getAliens() {
		
		return arep.findAll();
	}
	@RequestMapping("/alien/{aid}")
	@ResponseBody
	public java.util.Optional<Alien> getAlien(@PathVariable("aid") int aid) {
		
		return arep.findById(aid);
	}
}
