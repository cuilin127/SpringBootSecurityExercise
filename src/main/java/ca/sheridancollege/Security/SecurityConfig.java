package ca.sheridancollege.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;



@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	

	
	
	@Autowired	
	private AccessDeniedHandler hd;
	
	
	@Override
	public void configure(HttpSecurity http) throws Exception {		
		http.csrf().disable();
		http.headers().frameOptions().disable();
		http.authorizeRequests()
		.antMatchers("/student","/student/**").hasRole("STUDENT")
		.antMatchers("/prof","/prof/**").hasRole("PROFESSOR")
		//Define URLS and who has access
		.antMatchers("/h2-console/**").permitAll()
		.antMatchers("/","/**").permitAll()
		.anyRequest().authenticated()
		//Define our custom login page
		.and()
		.formLogin().loginPage("/login")
		.permitAll()
		//Define the logout
		.and()
		.logout()
		.invalidateHttpSession(true)
		.clearAuthentication(true)
		.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.logoutSuccessUrl("/login?logout")
				.permitAll()
		//Add access denied handler
				.and()
				.exceptionHandling()
				.accessDeniedHandler(hd);
				
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
		}
	
	@Autowired
	StudentDetailedServiceImpl studentDetailedService;
	
	
	@Autowired
	ProfessorDetailedServiceImpl professorDetailedService;
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
		auth.userDetailsService(studentDetailedService)
		.passwordEncoder(passwordEncoder());
		
		auth.userDetailsService(professorDetailedService)
		.passwordEncoder(passwordEncoder());
		
		auth.inMemoryAuthentication().passwordEncoder(NoOpPasswordEncoder.getInstance())
		.withUser("user").password("123").roles("PROFESSOR");
		}
}
