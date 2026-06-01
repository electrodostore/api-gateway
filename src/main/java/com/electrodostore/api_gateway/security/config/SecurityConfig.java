package com.electrodostore.api_gateway.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

import static org.springframework.http.HttpMethod.POST;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    //Define reglas para la cadena de filtros de seguridad
    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)

                //Gestiona el acceso general a las rutas de los microservicios
                .authorizeExchange(auth -> auth
                        .pathMatchers(POST, "/api/auth/**").permitAll()
                        .pathMatchers(HttpMethod.GET, "/api/productos").permitAll()
                        .pathMatchers(HttpMethod.GET, "/api/productos/{id_producto}").permitAll()
                        .anyExchange().authenticated()
                )

                /**
                 * Configura el Gate-Way como OAuth2 Resource Server
                 * para validar automáticamente tokens JWT.
                 */
                .oauth2ResourceServer(oauth -> oauth
                        .jwt(Customizer.withDefaults())
                )

                .build();
        }
}
