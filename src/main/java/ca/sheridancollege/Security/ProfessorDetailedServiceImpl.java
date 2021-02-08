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
import ca.sheridancollege.Repositoryies.ProfessorRepository;

@Service
public class ProfessorDetailedServiceImpl implements UserDetailsService {
	@Autowired
	@Lazy
	private ProfessorRepository prp;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		ca.sheridancollege.Beans.Professor professor = prp.findByUserName(username);
		if (professor == null) {
			System.out.println("Not Found");
			throw new UsernameNotFoundException("User " + username + "Not found");
		}
		
		List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
		Role role = professor.getRole();
		grantList.add(new SimpleGrantedAuthority(role.getRolename()));
		
		UserDetails userDetails = (UserDetails) new User(professor.getUserName(), professor.decodePassword(), grantList);
		return userDetails;
	}

}
