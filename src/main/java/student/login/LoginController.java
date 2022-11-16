package student.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableScheduling
public class LoginController {
	@Autowired
	UserRepo repo;

	@Autowired
	customJwtAuthenticationManager jwtAuthenticationManager;

	@PostMapping("signup")
	public ResponseEntity<Object> signup(@RequestBody User user) {
		repo.save(user);
		return new ResponseEntity<Object>("signed up successfully", HttpStatus.CREATED);
	}

	@GetMapping("/welcome")
	public String welcome() {
		return "you have logged in if you are seeing this page";
	}
   
	@PostMapping("/authenticate")
	
	public ResponseEntity<?> Authenticate(@RequestBody JwtRequest jwtRequest){
		 
		return jwtAuthenticationManager.doAuthentication(jwtRequest.getUsername(),jwtRequest.getPassword()) ;
		
		
	}
	
}
