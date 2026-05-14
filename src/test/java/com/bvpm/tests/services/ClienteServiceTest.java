package com.bvpm.tests.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bvpm.dtos.ClienteDTO;
import com.bvpm.entities.Cliente;
import com.bvpm.repositories.ClienteRepository;
import com.bvpm.services.ClienteService;

@ExtendWith(MockitoExtension.class) // Habilita el uso de Mocks de Mockito
class ClienteServiceTest {

    @Mock
    private ClienteRepository repository; // Simulamos la capa de datos

    @InjectMocks
    private ClienteService service; // Inyecta automáticamente el mock del repository en el service

    @Test
    void testBuscarClientePorId_Exitoso() {
        // Arrange: Preparamos el escenario
        Cliente cliente = new Cliente("Juan", "juan@mail.com", null, null, true);
        cliente.setIdCliente(1L);
        // Cuando el repo busque el ID 1, devolverá nuestro cliente falso
        when(repository.findById(1L)).thenReturn(Optional.of(cliente));

        // Act: Ejecutamos el método a probar
        ClienteDTO resultado = service.buscarClientePorId(1L);

        // Assert: Verificamos que los datos mapeados al DTO sean correctos
        assertNotNull(resultado);
        assertEquals("Juan", resultado.getNombre());
        verify(repository).findById(1L); // Verifica que se llamó al método del repo
    }

    @Test
    void testModificarCliente_Exitoso() {
        // Arrange: Cliente existente y DTO con nuevos cambios
        Long id = 1L;
        Cliente clienteEnDb = new Cliente("Nombre Viejo", "viejo@mail.com", null, null, true);
        ClienteDTO cambios = new ClienteDTO(null, "Nombre Nuevo", "nuevo@mail.com");
        
        when(repository.findById(id)).thenReturn(Optional.of(clienteEnDb));

        // Act: Modificamos
        ClienteDTO resultado = service.modificarCliente(id, cambios);

        // Assert: Verificamos que la ENTIDAD se actualizó antes de guardarse
        assertEquals("Nombre Nuevo", clienteEnDb.getNombre());
        assertEquals("nuevo@mail.com", clienteEnDb.getEmail());
        verify(repository).save(clienteEnDb); // Es vital asegurar que se llamó al save
    }

    @Test
    void testListarHabilitados() {
        // Arrange: Simulamos una lista de la base de datos
        List<Cliente> listaDb = Arrays.asList(
            new Cliente("A", "a@mail.com", null, null, true),
            new Cliente("B", "b@mail.com", null, null, true)
        );
        when(repository.findByHabilitadoTrue()).thenReturn(listaDb);

        // Act
        List<ClienteDTO> resultado = service.listarHabilitados();

        // Assert
        assertEquals(2, resultado.size());
        verify(repository).findByHabilitadoTrue();
    }
}
