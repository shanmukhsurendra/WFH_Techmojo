package com.example.demo;

import org.springframework.data.repository.CrudRepository;

public interface CreditCardDao extends CrudRepository<CreditCard, Integer>{

//	CreditCard findByCardNum(String cardNum);

//	Object findByCardNum(String cardNum);

	CreditCard findByCardNum(String cardNum);

}
