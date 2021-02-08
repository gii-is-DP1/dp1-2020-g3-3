package org.springframework.samples.aerolineasAAAFC.service;


import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.samples.aerolineasAAAFC.model.Billete;
import org.springframework.samples.aerolineasAAAFC.model.Cliente;
import org.springframework.samples.aerolineasAAAFC.repository.ClienteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ClienteService{

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
		
		if(cliente.getId() == null) {
			cliente.setVersion(1);
			clienteRepository.save(cliente);
			log.info("Cliente {} guardado.", cliente.getId());
			
			userService.saveUser(cliente.getUser());
			authoritiesService.saveAuthorities(cliente.getUser().getUsername(), "cliente");
		}else {
			cliente.setVersion(cliente.getVersion()+1);
			clienteRepository.save(cliente);
			log.info("Cliente {} actualizado.", cliente.getId());
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
	public Page<Cliente> findClientes(Pageable pageable){
		return clienteRepository.findAll(pageable);
	}
	
	public Collection<Cliente> findClientesNoPageable() {
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
		log.info("Cliente {} eliminado.", id);
		clienteRepository.deleteById(id);
	}
	
	// Historia 8: Sacar billetes comprados
	@Transactional
	public Page<Billete> findBilletesByIdCliente(int idCliente, Pageable pageable){
		Set<Billete> billetes = clienteRepository.findById(idCliente).get().getBilletes();
		Page<Billete> res = new PageImpl<Billete>(billetes.stream().collect(Collectors.toList()));
		return res;
	}
}
