package com.example.demo.controllers;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.CreditCard;
import com.example.demo.CreditCardDao;
import com.example.demo.User;
import com.example.demo.UserDao;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserDao dao;
	@Autowired
	private CreditCardDao cdao;
	private static final Logger log = LoggerFactory.getLogger(UserController.class);
	@PostMapping("/applyCreditCard")
	public String applyCreditCard(@RequestParam String name, @RequestParam String address, @RequestParam int phoneNumber, Model model) {
		Random rand = new Random();
		
//		dao.save(user);
		User user = new User(address,name,phoneNumber);
		User currentUser = dao.findByName(user.getName());
		if(currentUser != null) {
			return "user already exists with this data";
		}
//		log.info(uname + "     user name -------------------");
		CreditCard ccard = new CreditCard();
		if(user.getCardNum() == null) {
			ccard.setName(user.getName());
			
			ccard.setCreditLimit(rand.nextInt(50000));
			ccard.setCashLimit(ccard.getCreditLimit()/4);
			cdao.save(ccard);
			String expiryDate = String.valueOf(1+rand.nextInt(12)) + "/" + String.valueOf(21 + rand.nextInt(20));
			ccard.setExpiryDate(expiryDate);
			String cardnum = "";
			for(int i = 0; i < 16;i++) {
				cardnum += String.valueOf(rand.nextInt(9));
			}
			ccard.setCardNum(cardnum);
			ccard.setCardStatus("active");
		}
//		user.setCreditcard(ccard.getId());
		user.setCardNum(ccard.getCardNum());
		dao.save(user);
		Iterable<User> users =dao.findAll();
		model.addAttribute("users", users);
		
		return "index";
	}
	
	@GetMapping("/applyCreditCard")
	public String applyCreditCard() {
		
		return "applycreditcard";
	}
	
	
	@GetMapping("/transaction/{cardNum}")
	public String transaction(Model model, @PathVariable String cardNum) {
		CreditCard card = cdao.findByCardNum(cardNum);
		model.addAttribute("card", card);
		return "onlinetransaction";
	}
	
	
	@PostMapping("/transaction")
	public String transaction(@RequestParam String cardNum, @RequestParam String expiryDate,@RequestParam int amount,@RequestParam String name, Model model) {
		String message = "";
		CreditCard card = cdao.findByCardNum(cardNum);
		User user = dao.findByCardNum(cardNum);
		if(!card.getExpiryDate().equals(expiryDate)) {
			return "check expiry date";
		}
		if (!card.getName().equals(name)) {
			return "check your name";
		}
		if(!card.getCardStatus().equals("active")) {
			return "tansaction not possible please check your card status";
		}
		int cash = card.getCreditLimit()-amount;
		if(cash > 0 && cash > card.getCashLimit()) {
			card.setCreditLimit(cash);
			card.setCurrentCreditDue(amount + card.getCurrentCreditDue());
		} else {
			message = "no sufficient funds";
			return message;
		}
		
		if(card.getCreditLimit() < card.getCashLimit()) {
			card.setCashLimit(card.getCreditLimit());
			message = "transaction complete";
		}
		cdao.save(card);
		model.addAttribute("card", card);
		model.addAttribute("user", user);
		return "accountinfo";
		
	}
	
	@GetMapping("/withdrawl")
	public String withdrawl() {
		return "withdrawl";
	}
	@PostMapping("/withdrawl")
	public String withdrawl(@RequestParam String cardNum,@RequestParam String expiryDate,@RequestParam int amount, Model model) {
		String message = "";
		CreditCard card = cdao.findByCardNum(cardNum);
		User user = dao.findByCardNum(cardNum);
		if(!card.getCardStatus().equals("active")) {
			return "tansaction not possible please check your card status";
		}
		int cash = card.getCashLimit()-amount;
		if(cash > 0) {
			card.setCashLimit(cash);
			card.setCurrentCashDue(amount + card.getCurrentCashDue());
		} else {
			message = "no sufficient funds";
			return message;
		}
		cdao.save(card);
		model.addAttribute("card", card);
		model.addAttribute("user", user);
		return "accountinfo";
	}
	@GetMapping("/eomp/{cardNum}")
	public String eomp(@PathVariable String cardNum,Model model) {
//		String message = "";
		try {
		CreditCard card = cdao.findByCardNum(cardNum);
		User user = dao.findByCardNum(cardNum);

		if(card.getMinimumDue() > 0) {
			if(card.getLateCharges() >= 3) {
				card.setCardStatus("Delinquent");
				cdao.save(card);
				int totalOne = card.getCurrentCashDue() + card.getCurrentCreditDue() + card.getPastCashDue() + card.getPastCreditdDue();
				totalOne += totalOne/50;
				totalOne = totalOne/4;
				
				return "card is in delinquent status please clear shown to amount get card into active statu)s-->" + totalOne;
			} else {
				card.setLateCharges(card.getLateCharges()+1);
				card.setMinimumDue(card.getMinimumDue() + 100);
				cdao.save(card);
			}
		}
		
		int total = card.getCurrentCashDue() + card.getCurrentCreditDue() + card.getPastCashDue() + card.getPastCreditdDue();
		total += total/50;
		card.setPastCashDue(card.getPastCashDue() + card.getCurrentCashDue());
		card.setCurrentCashDue(0);
		card.setPastCreditDue(card.getPastCreditdDue() + card.getCurrentCreditDue());
		card.setCurrentCreditDue(0);
		card.setMinimumDue(total/20);
		cdao.save(card);
		model.addAttribute("total",total);
		model.addAttribute("card", card);
		model.addAttribute("user", user);
		return "billpage";
		} catch (Exception e){
			
			return "billpage";
		}
	}
	//this method is to get the balance for certain card which takes card number as input parameter and
	//gives balances to be paid.
//	@RequestMapping("/balanceEnquiry")
//	public String balanceEnquiry(@RequestParam String cardNum) {
////		String message = "";
//		try {
//		CreditCard card = cdao.findByCardNum(cardNum);
//		int total = card.getCurrentCashDue() + card.getCurrentCreditDue() + card.getPastCashDue() + card.getPastCreditdDue();
//		total += total/50;
//		int min = total/20;
//		int overallCashDue = card.getPastCashDue() + card.getCurrentCashDue();
//		int overallCreditDue = card.getPastCreditDue() + card.getCurrentCreditDue();
//		System.out.println("cash due to be paid" + card.getCurrentCashDue() + card.getPastCashDue());
//		
//		return "amount to be paid -->" + total + "\n" +"minimum amount to be paid to avoid late charges -->" + min 
//				+ "\n" + "overall cashdue-->" + overallCashDue + "\n" + "overall creditdue-->" + overallCreditDue;
//		} catch (Exception e) {
//			return "check entered details";
//		}
//	}
		
	@PostMapping("/payment")
	public String doPayment(@RequestParam String cardNum, Model model, @RequestParam int amount) {
			CreditCard card = cdao.findByCardNum(cardNum);
			User user = dao.findByCardNum(cardNum);
			log.info("     user name -------------------");
			if (amount < card.getMinimumDue()) {
				return "should clear atleast minimumDue" + card.getMinimumDue();
			} else {
				amount = amount - card.getMinimumDue();
				card.setMinimumDue(0);
			}
			if(amount > card.getPastCashDue()) {
				amount = amount - card.getPastCashDue();
				card.setCashLimit(card.getCashLimit() + card.getPastCashDue());
				card.setPastCashDue(0);
			} else {
				card.setPastCashDue(card.getPastCashDue()-amount);
				card.setCashLimit(card.getCashLimit() + amount);
				amount = 0;
			}
			if(amount > card.getPastCreditDue()) {
				amount = amount - card.getPastCreditDue();
				card.setCreditLimit(card.getCreditLimit() + card.getPastCreditDue());
				card.setPastCreditDue(0);
			} else {
				card.setPastCreditDue(card.getPastCreditDue()-amount);
				card.setCreditLimit(card.getCreditLimit() + amount);
				amount = 0;
			}
			if(amount > card.getCurrentCashDue()) {
				amount = amount - card.getCurrentCashDue();
				card.setCashLimit(card.getCashLimit() + card.getCurrentCashDue());
				card.setCurrentCashDue(0);
			} else {
				card.setCurrentCashDue(card.getCurrentCashDue()-amount);
				card.setCashLimit(card.getCashLimit() + amount);
				amount = 0;
			}
			if(amount > card.getCurrentCreditDue()) {
				amount = amount - card.getCurrentCreditDue();
				card.setCreditLimit(card.getCreditLimit() + card.getCurrentCreditDue());
				card.setCurrentCreditDue(0);
			} else {
				card.setCurrentCreditDue(card.getCurrentCreditDue()-amount);
				card.setCreditLimit(card.getCreditLimit() + amount);
				amount = 0;
			}
			if(card.getMinimumDue() == 0) {
				card.setLateCharges(0);
				card.setCardStatus("active");
			}
			cdao.save(card);
			model.addAttribute("card", card);
			model.addAttribute("user", user);
			return "accountinfo";
			}
	
	
	
	@GetMapping("/payment/{cardNum}")
	public String payments(@PathVariable String cardNum, Model model) {
//		String message = "";
		CreditCard card = cdao.findByCardNum(cardNum);
		model.addAttribute("card", card);
		return "payment";
	}
	
	@GetMapping("/closecard")
	public String closcard() {
		return "closecard";
	}
	@PostMapping("/closecard")
	public String closeCard(@RequestParam String cardNum) {
		try {
		CreditCard card = cdao.findByCardNum(cardNum);
		int total = card.getCurrentCashDue() + card.getCurrentCreditDue() + card.getPastCashDue() + card.getPastCreditdDue() + card.getMinimumDue();
		if(total == 0 && card.getCardStatus().equals("active")) {
			card.setCardStatus("closed");
			cdao.save(card);
			return "card closed";
		}
		if(!card.getCardStatus().equals("active")) {
			return "please check card status";
		}
		return "transaction unseccusful";
		} catch (Exception e) {
			return "check enetered card details";
		}
	}
}
