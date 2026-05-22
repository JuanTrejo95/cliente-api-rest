package com.bvpm.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        
        // 1. Extraer la cabecera 'Authorization' de la petición HTTP
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        // 2. Si no viene token o no empieza con "Bearer ", delegamos a la cadena de filtros
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 3. Cortar el texto para obtener el token JWT puro (quitamos "Bearer ")
        jwt = authHeader.substring(7);
        
        // 4. Extraer el username/email usando el JwtService
        userEmail = jwtService.extractUsername(jwt);

        // 5. Si hay un email y el usuario no está ya autenticado en este hilo...
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            
            // 6. Validamos la firma y expiración del token
            if (jwtService.isTokenValid(jwt, userEmail)) {
                
                // 7. Creamos el objeto de autenticación de Spring Security
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userEmail,
                        null,
                        null // Aquí se mapearán los roles más adelante
                );
                
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                
                // 8. Inyectamos al usuario en el contexto de seguridad de la app
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        
        // 9. Continuar al siguiente eslabón (otro filtro o nuestro Controller)
        filterChain.doFilter(request, response);
    }
}