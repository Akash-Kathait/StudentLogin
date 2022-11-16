package student.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class customJwtAuthenticationManager {

	@Autowired
	AuthenticationManager authenticationManager;
	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	public ResponseEntity<?> doAuthentication(String username, String password) {

		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (BadCredentialsException e) {
			e.printStackTrace();
		} catch (AuthenticationException e) {
			e.printStackTrace();
		}

		final UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

		final String token = jwtTokenUtil.generateToken(userDetails);

		return ResponseEntity.ok(new JwtResponse(token));

	}

}
