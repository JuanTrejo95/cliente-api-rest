package com.bvpm.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.bvpm.dtos.ClienteDTO;
import com.bvpm.services.ClienteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/api/v1/clientes") 
@Tag(name = "Cliente", description = "Endpoints para la gestión de clientes en el sistema")
public class ClienteController {

    @Autowired
    private ClienteService service;
    
    @Operation(summary = "Registrar nuevo cliente", description = "Crea un cliente en la base de datos y retorna sus datos con ID asignado")
    @ApiResponse(responseCode = "201", description = "Cliente creado exitosamente")
    @PostMapping
    public ResponseEntity<ClienteDTO> guardar(@Validated @RequestBody ClienteDTO clienteDTO){
        return new ResponseEntity<>(service.guardarCliente(clienteDTO), HttpStatus.CREATED);
    }
    
    @Operation(summary = "Listar clientes activos", description = "Retorna una lista de todos los clientes que no han sido inhabilitados")
    @GetMapping("/all")
    public ResponseEntity<List<ClienteDTO>> obtenerTodosHabilitados() {
        return ResponseEntity.ok(service.listarHabilitados());
    }
    
    @Operation(summary = "Buscar cliente por ID", description = "Obtiene los detalles de un cliente específico mediante su identificador único")
    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> obtenerClientePorId(@PathVariable Long id) { 
        return ResponseEntity.ok(service.buscarClientePorId(id));
    }
    
    @Operation(summary = "Actualizar cliente", description = "Modifica los datos de un cliente existente. Es una operación idempotente")
    @PutMapping("/{id}")
    public ResponseEntity<ClienteDTO> actualizarCliente(@PathVariable Long id, @Validated @RequestBody ClienteDTO clienteDTO) {
        return ResponseEntity.ok(service.modificarCliente(id, clienteDTO));
    }
    
    @Operation(summary = "Inhabilitar cliente", description = "Realiza un borrado lógico del cliente cambiando su estado a inhabilitado")
    @ApiResponse(responseCode = "204", description = "Cliente inhabilitado correctamente")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.inhabilitarCliente(id);
        return ResponseEntity.noContent().build();
    }
}