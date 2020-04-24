package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import net.bytebuddy.dynamic.DynamicType.Builder.FieldDefinition.Optional;

@RestController
public class AlienController {
	@Autowired
	AlienRepo arep;

	@RequestMapping("/")
	public String home() {
		return "home.jsp";
	}

	@PostMapping("/alien")
	public Alien addAlien(@RequestBody Alien alien) {
		arep.save(alien);
		return alien;
	}

	@DeleteMapping("/alien")
	public String deleteAlien(@PathVariable int aid) {
		Alien a = arep.getOne(aid);
		arep.delete(a);
		return "deleted";
	}

	@PutMapping("/alien")
	public Alien saveOrUpdateAlien(@RequestBody Alien alien) {
		arep.save(alien);
		return alien;
	}
	@GetMapping(path = "/aliens", produces = { "applications/xml" })
	public List<Alien> getAliens() {

		return arep.findAll();
	}

	@RequestMapping("/alien/{aid}")
	public java.util.Optional<Alien> getAlien(@PathVariable("aid") int aid) {

		return arep.findById(aid);
	}
}
