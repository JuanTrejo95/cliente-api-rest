package com.bvpm.tests.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.bvpm.controllers.ClienteController;
import com.bvpm.dtos.ClienteDTO;
import com.bvpm.services.ClienteService;

@WebMvcTest(ClienteController.class) // Carga solo la configuración web de Spring
class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc; // Herramienta para simular peticiones HTTP (GET, POST, etc.)

    @MockitoBean
    private ClienteService service; // Creamos un mock del service para el contexto de Spring

    @Autowired
    private tools.jackson.databind.ObjectMapper objectMapper; // Herramienta para convertir Objetos Java <-> JSON

    @Test
    void testGuardar_Retorna201() throws Exception {
        // Arrange: Datos de entrada y salida esperada
        ClienteDTO dtoEntrada = new ClienteDTO(null, "Juan", "juan@mail.com");
        ClienteDTO dtoSalida = new ClienteDTO(1L, "Juan", "juan@mail.com");
        
        when(service.guardarCliente(any(ClienteDTO.class))).thenReturn(dtoSalida);

        // Act & Assert
        mockMvc.perform(post("/cliente") // Petición POST
                .contentType(MediaType.APPLICATION_JSON) // Indicamos que enviamos JSON
                .content(objectMapper.writeValueAsString(dtoEntrada))) // Cuerpo del mensaje
                .andExpect(status().isCreated()) // Verificamos status 201
                .andExpect(jsonPath("$.id").value(1)) // Verificamos campo "id" en el JSON de respuesta
                .andExpect(jsonPath("$.nombre").value("Juan"));
    }

    @Test
    void testObtenerHabilitadoPorId_Retorna200() throws Exception {
        // Arrange
        ClienteDTO salida = new ClienteDTO(1L, "Juan", "juan@mail.com");
        when(service.buscarClientePorId(1L)).thenReturn(salida);

        // Act & Assert
        mockMvc.perform(get("/cliente/1")) // Petición GET
                .andExpect(status().isOk()) // Verificamos status 200
                .andExpect(jsonPath("$.email").value("juan@mail.com"));
    }

    @Test
    void testEliminar_Retorna204() throws Exception {
        // Act & Assert
        mockMvc.perform(delete("/cliente/1")) // Petición DELETE
                .andExpect(status().isNoContent()); // Verificamos status 204 (Sin contenido)
    }
}
