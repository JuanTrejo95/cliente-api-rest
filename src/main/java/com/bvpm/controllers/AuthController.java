package com.bvpm.controllers;

import com.bvpm.dtos.AuthRequest;
import com.bvpm.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        // Validación simulada (De pruebas)
        if ("juan.dev@email.com".equals(authRequest.getUsername()) && "password123".equals(authRequest.getPassword())) {
            
            // Si las credenciales son correctas, fabricamos su brazalete JWT
            String token = jwtService.generateToken(authRequest.getUsername());
            
            // Preparamos la respuesta en un JSON estructurado
            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            response.put("token_type", "Bearer");
            response.put("expires_in", "30 minutes");
            
            return ResponseEntity.ok(response);
        }
        
        // Si el usuario se equivoca, regresamos un 401 Unauthorized explícito
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "Credenciales incorrectas");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }
}