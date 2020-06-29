package de.hska.user.core.service.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserSecurityService implements UserDetailsService {

	@Autowired
	private UserRepo repo;
	
	@Override
	public UserDetails loadUserByUsername(String username) 
			throws UsernameNotFoundException {
		User user = repo.findByUsername(username).get(0);
		return new org.springframework.security.core.userdetails
				.User(user.getUsername(), user.getPassword(),
	            List.of(new SimpleGrantedAuthority(user.getRoletype())));
	}

}
