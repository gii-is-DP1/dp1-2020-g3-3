package org.springframework.samples.aerolineasAAAFC.service;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.aerolineasAAAFC.model.User;
import org.springframework.samples.aerolineasAAAFC.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserService{

	private UserRepository userRepository;
	
	private static final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

	@Autowired
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;

	}

	@Transactional
	public void saveUser(User user) throws DataAccessException {
		
		log.info("Contraseña: {}", user.getPassword());
		String BCrpytPss = bCryptPasswordEncoder.encode(user.getPassword());
		
		user.setPassword(BCrpytPss);
		log.info("Contraseña con BCrypt: {}", user.getPassword());
		user.setMatchingPassword(BCrpytPss);	
		log.info("Contraseña confirmada con BCrypt: {}", user.getMatchingPassword());
		
		userRepository.save(user);
		
		log.info("Se ha creado un nuevo usuario: {}", user.getUsername());
	}
	
	public Optional<User> findUser(String username) {
		return userRepository.findById(username);
	}
}
