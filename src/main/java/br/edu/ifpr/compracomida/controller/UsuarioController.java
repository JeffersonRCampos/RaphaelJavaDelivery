package br.edu.ifpr.compracomida.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import br.edu.ifpr.compracomida.model.Usuario;
import br.edu.ifpr.compracomida.repositorio.UsuarioRepositorio;

import java.util.Optional;
import java.util.UUID;

@RestController // Define que esta classe é um controlador REST
@RequestMapping("/usuarios") // Define o endpoint base para operações relacionadas a usuários
public class UsuarioController {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio; // Injeção do repositório de usuários

    @Autowired
    private PasswordEncoder passwordEncoder; // Injeção do encoder para criptografar senhas

    // Método para cadastro de novo usuário
    @PostMapping("/cadastro")
    public ResponseEntity<String> cadastrarUsuario(@RequestBody Usuario usuario) {
        // Verifica se o email já está registrado
        if (usuarioRepositorio.findByEmail(usuario.getEmail()).isPresent()) {
            return new ResponseEntity<>("Usuário já cadastrado", HttpStatus.BAD_REQUEST); // Retorna erro se o email já estiver em uso
        }
        // Criptografa a senha antes de salvar
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        usuarioRepositorio.save(usuario); // Salva o usuário no banco de dados
        return new ResponseEntity<>("Usuário cadastrado com sucesso!", HttpStatus.CREATED); // Resposta de sucesso
    }

    // Método para login de usuário
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Usuario usuario) {
        // Busca o usuário pelo email fornecido
        Optional<Usuario> usuarioExistente = usuarioRepositorio.findByEmail(usuario.getEmail());

        // Verifica se o usuário existe e se a senha fornecida está correta
        if (usuarioExistente.isPresent() && passwordEncoder.matches(usuario.getSenha(), usuarioExistente.get().getSenha())) {
            // Gera um token aleatório (UUID) para autenticação
            String token = UUID.randomUUID().toString();
            usuarioExistente.get().setChave(token); // Armazena o token no campo 'chave'
            usuarioRepositorio.save(usuarioExistente.get()); // Atualiza o usuário com o token
            return new ResponseEntity<>(token, HttpStatus.OK); // Retorna o token gerado
        } else {
            return new ResponseEntity<>("Login ou senha inválidos", HttpStatus.UNAUTHORIZED); // Retorna erro se as credenciais forem inválidas
        }
    }
}