package com.example.demo;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserDao dao;
	@Autowired
	private CreditCardDao cdao;

	@PostMapping("/applyCreditCard")
	public String applyCreditCard(@RequestBody User user) {
		Random rand = new Random();
//		dao.save(user);
//		@Autowired
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
		
		
		
		return "cardApplied";
	}
	@PutMapping("/transaction")
	public String transaction(@RequestParam String cardNum, @RequestParam String expiryDate,@RequestParam int amount,@RequestParam String name) {
		String message = "";
		CreditCard card = cdao.findByCardNum(cardNum);
		int cash = card.getCreditLimit()-amount;
		if(cash > 0 && cash > card.getCashLimit()) {
			card.setCreditLimit(cash);
			card.setCurrentCreditDue(amount + card.getCurrentCashDue());
		} else {
			message = "no sufficient funds";
			return message;
		}
		
		if(card.getCreditLimit() < card.getCashLimit()) {
			card.setCashLimit(card.getCreditLimit());
			message = "transaction complete";
		}
		cdao.save(card);
		return message;
	}
	@PutMapping("/withdrawl")
	public String withdrawl(@RequestParam String cardNum,@RequestParam int amount) {
		String message = "";
		CreditCard card = cdao.findByCardNum(cardNum);
		int cash = card.getCashLimit()-amount;
		if(cash > 0) {
			card.setCashLimit(cash);
			card.setCurrentCashDue(amount + card.getCurrentCashDue());
		} else {
			message = "no sufficient funds";
			return message;
		}
		cdao.save(card);
		return message;
	}
	@PutMapping("/eomp")
	public String eomp(@RequestParam String cardNum) {
//		String message = "";
		CreditCard card = cdao.findByCardNum(cardNum);
		int total = card.getCurrentCashDue() + card.getCurrentCreditDue() + card.getPastCashDue() + card.getPastCreditdDue();
		total += total/50;
		card.setPastCashDue(card.getPastCashDue() + card.getCurrentCashDue());
		card.setCurrentCashDue(0);
		card.setPastCreditDue(card.getPastCreditdDue() + card.getCurrentCreditDue());
		card.setCurrentCreditDue(0);
		card.setMinimumDue(total/20);
		cdao.save(card);
		return "amount to be paid" + total;
	}
	//this method is to get the balance for certain card which takes card number as input parameter and
	//gives balances to be paid.
	@RequestMapping("/balanceEnquiry")
	public String balanceEnquiry(@RequestParam String cardNum) {
//		String message = "";
		CreditCard card = cdao.findByCardNum(cardNum);
		int total = card.getCurrentCashDue() + card.getCurrentCreditDue() + card.getPastCashDue() + card.getPastCreditdDue();
		total += total/50;
		int min = total/20;
		int overallCashDue = card.getPastCashDue() + card.getCurrentCashDue();
		int overallCreditDue = card.getPastCreditDue() + card.getCurrentCreditDue();
		System.out.println("cash due to be paid" + card.getCurrentCashDue() + card.getPastCashDue());
		
		return "amount to be paid -->" + total + "\n" +"minimum amount to be paid to avoid late charges -->" + min 
				+ "\n" + "overall cashdue-->" + overallCashDue + "\n" + "overall creditdue-->" + overallCreditDue;
	}
//	@PutMapping("/eomp")
//	public String eomp(@RequestParam String cardNum) {
////		String message = "";
//		CreditCard card = cdao.findByCardNum(cardNum);
//		int total = card.getCurrentCashDue() + card.getCurrentCreditDue() + card.getPastCashDue() + card.getPastCreditdDue();
//		total += total/50;
//		card.setPastCashDue(card.getPastCashDue() + card.getCurrentCashDue());
//		card.setCurrentCashDue(0);
//		card.setPastCreditDue(card.getPastCreditdDue() + card.getCurrentCreditDue());
//		card.setCurrentCreditDue(0);
//		cdao.save(card);
//		return "amount to be paid" + total;
//	}
}
