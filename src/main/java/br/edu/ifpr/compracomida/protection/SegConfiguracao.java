package br.edu.ifpr.compracomida.protection;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SegConfiguracao {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, FiltroToken filtroToken) throws Exception {
        return http
            .csrf(csrf -> csrf.disable()) // Nova sintaxe para desativar o CSRF
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/usuarios/cadastro", "/usuarios/login").permitAll() // Permite acesso livre para cadastro e login
                .anyRequest().authenticated() // Exige autenticação para qualquer outra rota
            )
            .addFilterBefore(filtroToken, UsernamePasswordAuthenticationFilter.class) // Adiciona o filtro de token antes do filtro de autenticação padrão
            .build(); // Constroi e retorna o filtro de segurança
    }
}