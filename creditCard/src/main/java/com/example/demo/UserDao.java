package com.example.demo;

import org.springframework.data.repository.CrudRepository;

public interface UserDao extends CrudRepository<User, Integer>{

	User findByName(String name);

	String findByAddress(String address);

	int findByPhoneNumber(int phoneNumber);

	User findByCardNum(String cardNum);

}
