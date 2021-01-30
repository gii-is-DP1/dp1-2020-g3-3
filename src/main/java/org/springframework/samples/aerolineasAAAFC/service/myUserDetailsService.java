package org.springframework.samples.aerolineasAAAFC.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.aerolineasAAAFC.model.Authorities;
import org.springframework.samples.aerolineasAAAFC.model.User;
import org.springframework.samples.aerolineasAAAFC.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class myUserDetailsService implements UserDetailsService{

	
	@Autowired
	private UserRepository repository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		log.info("username: {}", username);
		
		User user = repository.findByUsername(username);
		
        log.info("USER: {}", user);
        
		if (user == null) {
            throw new UsernameNotFoundException("No user found with username: "+ username);
        }	
        
        boolean enabled = true;
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;
        
        List<GrantedAuthority> authorities = getAuthority(user.getAuth());
        
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), enabled, accountNonExpired, 
		          credentialsNonExpired, accountNonLocked, authorities);
	}

	private List<GrantedAuthority> getAuthority(Set<Authorities> set) {
		List<GrantedAuthority> authorities = new ArrayList<>();
		
		for (Authorities autho : set) {
			String role = autho.getAuthority();
            authorities.add(new SimpleGrantedAuthority(role));
        }
		
		log.info("Autoridades del usuario: {}", authorities);
		
		return authorities;
	}

}
