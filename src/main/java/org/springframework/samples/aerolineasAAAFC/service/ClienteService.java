package org.springframework.samples.aerolineasAAAFC.service;


import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.aerolineasAAAFC.model.Cliente;
import org.springframework.samples.aerolineasAAAFC.repository.ClienteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClienteService {
	
	private ClienteRepository clienteRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthoritiesService authoritiesService;
	
	@Autowired
	public ClienteService(ClienteRepository clienteRepository) {
		this.clienteRepository = clienteRepository;
	}
	
	@Transactional
	public void saveCliente(Cliente cliente) throws DataAccessException{
		clienteRepository.save(cliente);
		userService.saveUser(cliente.getUser());
		authoritiesService.saveAuthorities(cliente.getUser().getUsername(), "cliente");
	}
	
	public Cliente findClienteById(Integer id){
		return clienteRepository.findById(id).get();
	}
	
	public Collection<Cliente> findClientes(){
		return StreamSupport.stream(clienteRepository.findAll().spliterator(), false)
			    .collect(Collectors.toList());
	}
	
	public void deleteClienteById(int id) throws DataAccessException{
		clienteRepository.deleteById(id);
	}
}
