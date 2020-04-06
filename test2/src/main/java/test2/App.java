package test2;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
	public static void main(String args[]) {
		ApplicationContext contxt = new ClassPathXmlApplicationContext("test2.spring.xml");
		Tyre obj = (Tyre)contxt.getBean("tyre");
		obj.toString();
	}
}
