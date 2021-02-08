package ca.sheridancollege.Security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ca.sheridancollege.Beans.Role;
import ca.sheridancollege.Repositoryies.StudentRepository;

@Service
public class StudentDetailedServiceImpl implements UserDetailsService {
	
	@Autowired
	@Lazy
	private StudentRepository srp;
	
	
	@Override
	public UserDetails loadUserByUsername(String username) 
			throws UsernameNotFoundException {
		
		ca.sheridancollege.Beans.Student student = srp.findByName(username);
		if (student == null) {
			System.out.println("Not Found");
			throw new UsernameNotFoundException("User " + username + "Not found");
		}
		
		List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
		Role role = student.getRole();
		grantList.add(new SimpleGrantedAuthority(role.getRolename()));
		
		UserDetails userDetails = (UserDetails) new User(student.getName(), student.decodeStudentId(), grantList);
		return userDetails;
	}

}
