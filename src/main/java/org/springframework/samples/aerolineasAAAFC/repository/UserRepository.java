package org.springframework.samples.aerolineasAAAFC.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.aerolineasAAAFC.model.User;


public interface UserRepository extends  CrudRepository<User, String>{
	
}
