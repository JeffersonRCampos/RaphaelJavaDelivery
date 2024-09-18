package br.edu.ifpr.compracomida.protection;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import br.edu.ifpr.compracomida.model.Usuario;
import br.edu.ifpr.compracomida.repositorio.UsuarioRepositorio;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FiltroToken extends OncePerRequestFilter {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio; // Injeção do repositório de usuários

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Se a requisição for para cadastro ou login, não exige autenticação
        if (request.getRequestURI().equals("/usuarios/cadastro") || request.getRequestURI().equals("/api/usuarios/login")) {
            filterChain.doFilter(request, response); // Prossegue sem exigir o token
            return;
        }

        // Verifica se o cabeçalho da requisição contém o token
        var authenticationHeader = request.getHeader("Token");
        if (authenticationHeader != null) {
            // Busca o usuário com base no token fornecido
            Optional<Usuario> usuario = usuarioRepositorio.findByChave(authenticationHeader);

            if (usuario.isPresent()) {
                // Cria um token de autenticação e o coloca no contexto de segurança
                var authentication = new UsernamePasswordAuthenticationToken(usuario.get(), null, null);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                // Se o token não for válido, retorna 403 (proibido)
                response.setStatus(HttpStatus.FORBIDDEN.value());
                return;
            }
        } else {
            // Se o cabeçalho não contiver um token, retorna 403 (proibido)
            response.setStatus(HttpStatus.FORBIDDEN.value());
            return;
        }

        // Prossegue com o filtro, permitindo a requisição
        filterChain.doFilter(request, response);
    }
}