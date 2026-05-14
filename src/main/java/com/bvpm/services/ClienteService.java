package com.bvpm.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bvpm.dtos.ClienteDTO;
import com.bvpm.entities.Cliente;
import com.bvpm.repositories.ClienteRepository;

import jakarta.transaction.Transactional;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repository;
	
	@Transactional
	public ClienteDTO guardarCliente(ClienteDTO clienteDTO) {
		Cliente cliente = new Cliente(clienteDTO.getNombre(), clienteDTO.getEmail(), null, null, true);
		Cliente clienteGuardado = repository.save(cliente);
		clienteDTO.setId(clienteGuardado.getIdCliente());
		return clienteDTO;
	}
	
	public ClienteDTO buscarClientePorId(Long id) {
		return repository.findById(id)
				.filter(Cliente::isHabilitado)
				.map(this::convertirClienteADto)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
	}
	
	@Transactional
	public ClienteDTO modificarCliente(Long id, ClienteDTO clienteDTO) {
		Cliente cliente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
		
		cliente.setNombre(clienteDTO.getNombre());
		cliente.setEmail(clienteDTO.getEmail());
		clienteDTO.setId(cliente.getIdCliente());
		repository.save(cliente);
		
		return clienteDTO;
	}
	
	@Transactional
    public void inhabilitarCliente(Long id) {
		Cliente cliente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
		cliente.setHabilitado(false);
		repository.save(cliente);
    }
	
	public List<ClienteDTO> listarHabilitados() {
        return repository.findByHabilitadoTrue().stream()
                .map(this::convertirClienteADto)
                .collect(Collectors.toList());
    }
	
	public ClienteDTO convertirClienteADto(Cliente c) {
		return new ClienteDTO(c.getIdCliente(), c.getNombre(), c.getEmail());
	}
	
}
