package com.bvpm.config;

import com.bvpm.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // 1. Deshabilitamos CSRF (Cross-Site Request Forgery) ya que las APIs REST con JWT no usan cookies
            .csrf(csrf -> csrf.disable())
            
            // 2. Configuramos las reglas de acceso a las rutas (Endpoints)
            .authorizeHttpRequests(auth -> auth
                // Permitir acceso libre a Swagger para poder documentar y probar sin bloqueos
                .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                // Permitir acceso libre a los endpoints de autenticación (Login/Registro) en el futuro
                .requestMatchers("/api/v1/auth/**").permitAll()
                // CUALQUIER otra petición al API requerirá que el usuario esté autenticado
                .anyRequest().authenticated()
            )
            
            // 3. Le decimos a Spring que no guarde estado de sesión en memoria (Stateless)
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            
            // 4. Conectamos nuestro filtro personalizado ANTES del filtro de autenticación por defecto de Spring
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}