package org.springframework.samples.aerolineasAAAFC.service;


import java.util.Optional;

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
	public ClienteService(ClienteRepository clienteRepository) {
		this.clienteRepository = clienteRepository;
	}
	
	@Transactional
	public void saveCliente(Cliente cliente) throws DataAccessException{
		clienteRepository.save(cliente);
	}
	
	public Optional<Cliente> findCliente(Integer id){
		return clienteRepository.findById(id);
	}
	
	public void deleteClienteById(int id) throws DataAccessException{
		clienteRepository.deleteById(id);
	}
}
