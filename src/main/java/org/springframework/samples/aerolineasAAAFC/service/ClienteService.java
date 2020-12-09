package org.springframework.samples.aerolineasAAAFC.service;


import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.aerolineasAAAFC.model.Cliente;
import org.springframework.samples.aerolineasAAAFC.repository.ClienteRepository;
import org.springframework.samples.aerolineasAAAFC.service.exceptions.NifDuplicadoException;
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
	public void saveCliente(Cliente cliente) throws DataAccessException, NifDuplicadoException{
		Cliente aux = clienteRepository.findByNif(cliente.getNif());
		if(aux!=null) {
			throw new NifDuplicadoException();
		}else {
			clienteRepository.save(cliente);
			userService.saveUser(cliente.getUser());
			authoritiesService.saveAuthorities(cliente.getUser().getUsername(), "cliente");
		}
	}
	
	@Transactional(readOnly = true)
	public Cliente findClienteById(Integer id){
		return clienteRepository.findById(id).get();
	}
	
	@Transactional(readOnly = true)
	public Cliente findClienteByNif(String nif) {
		return clienteRepository.findByNif(nif);
	}
	
	@Transactional(readOnly = true)
	public Collection<Cliente> findClientes(){
		return StreamSupport.stream(clienteRepository.findAll().spliterator(), false)
			    .collect(Collectors.toList());
	}
	
	@Transactional
	public void deleteClienteById(int id) throws DataAccessException{
		clienteRepository.deleteById(id);
	}
}
