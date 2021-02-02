package org.springframework.samples.aerolineasAAAFC.service;


import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.samples.aerolineasAAAFC.model.Billete;
import org.springframework.samples.aerolineasAAAFC.model.Cliente;
import org.springframework.samples.aerolineasAAAFC.repository.ClienteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Slf4j
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
	public void saveCliente(Cliente cliente) throws DataIntegrityViolationException{
		
		clienteRepository.save(cliente);
		String cl = cliente.getApellidos() + ", " + cliente.getNombre();
		log.info("Cliente guardado: {}", cl);
		
		userService.saveUser(cliente.getUser());
		
		authoritiesService.saveAuthorities(cliente.getUser().getUsername(), "cliente");
		log.info("Autoridad establecida: {}", cliente.getUser().getAuth());
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

	@Transactional(readOnly = true)
	public Collection<Cliente> findClientesPorNombre(String nombre,String apellidos){
		return StreamSupport.stream(clienteRepository.findAll().spliterator(), false)
				.filter(x->x.getNombre().equals(nombre))
				.filter(x->x.getApellidos().equals(apellidos))
				.collect(Collectors.toList());
	}
	

	@Transactional
	public void deleteClienteById(int id) throws DataAccessException{
		clienteRepository.deleteById(id);
	}
	
	// Historia 8: Sacar billetes comprados
	@Transactional
	public Collection<Billete> findBilletesByIdCliente(int idCliente){
		return clienteRepository.findById(idCliente).get().getBilletes();
	}
}
