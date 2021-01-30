package org.springframework.samples.aerolineasAAAFC.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.aerolineasAAAFC.model.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends  CrudRepository<User, String>{
	
	public User findByUsername(String username);
	
	
}
