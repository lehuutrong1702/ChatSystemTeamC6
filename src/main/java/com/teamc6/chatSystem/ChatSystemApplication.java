package com.teamc6.chatSystem;

import com.teamc6.chatSystem.entity.Relationship;
import com.teamc6.chatSystem.entity.User;
import com.teamc6.chatSystem.service.RelationshipService;
import com.teamc6.chatSystem.service.UserService;
import com.teamc6.chatSystem.serviceImpl.RelationshipServiceImpl;
import com.teamc6.chatSystem.serviceImpl.UserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Map;
import java.util.Set;

@SpringBootApplication
public class ChatSystemApplication{
	private UserService userService;
	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(ChatSystemApplication.class, args);
		UserService userService = context.getBean(UserService.class);

		Date date = new Date(30/04/2003);
		User u = new User("Phan Van Nguyen", date, true, "nguyenpv@gmail.com", "nguyle", "30042003");
		userService.save(u);
		//SpringApplication.run(ChatSystemApplication.class, args);

	}

}

