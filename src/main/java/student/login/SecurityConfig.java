package student.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private CustomUserDetailsService customeUserDetailsService;

	@Autowired
	private JwtRequestFilter jwtRequestFilter;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(customeUserDetailsService).passwordEncoder(new BCryptPasswordEncoder());

	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		// configure AuthenticationManager so that it knows from where to load
		// user for matching credentials
		// Use BCryptPasswordEncoder
		auth.userDetailsService(customeUserDetailsService).passwordEncoder(new BCryptPasswordEncoder());
	}

//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//
//		http.csrf().disable();
//		http.authorizeRequests().mvcMatchers("/signup", "/authenticate").permitAll().anyRequest().authenticated().and()
//				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().formLogin();
//
//		// adding a filter
//		http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
//	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	  http.csrf().disable();
	  http.authorizeRequests().anyRequest().authenticated().and().oauth2Login() ;
	}
	
}
