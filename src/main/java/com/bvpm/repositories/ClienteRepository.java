package com.bvpm.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bvpm.entities.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long>{

	List<Cliente> findByHabilitadoTrue();
	
}
