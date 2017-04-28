package info.zhwan.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class SecurityStudyApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecurityStudyApplication.class, args);
	}
}

@RestController
class UserController {


	@GetMapping("/api/user")
	String userIndex() {
		return "hello user index";
	}
}