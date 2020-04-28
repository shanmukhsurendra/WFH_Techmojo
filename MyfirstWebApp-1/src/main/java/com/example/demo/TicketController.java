package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ticket")
public class TicketController {
	@Autowired
	private Ticketdao dao;
	@PostMapping("/booktickets")
	public String bookTicket(List<Ticket> ticket) {
		dao.saveAll(ticket);
		return "tickets saved";
	}
	@GetMapping("/getTickets")
	public List<Ticket> getTicket() {
		return (List<Ticket>) dao.findAll();
	}
}
