package test2;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
	public static void main(String args[]) {
		ApplicationContext contxt = new ClassPathXmlApplicationContext("spring.xml");
		Vehicle obj = (Vehicle)contxt.getBean("vehicle");
		obj.drive();
	}
}
