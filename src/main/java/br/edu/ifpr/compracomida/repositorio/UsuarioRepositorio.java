package br.edu.ifpr.compracomida.repositorio;

import br.edu.ifpr.compracomida.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository // Indica que esta interface é um repositório Spring para a entidade Usuario
public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {

    // Método para buscar um usuário pelo email
    Optional<Usuario> findByEmail(String email);

    // Método para buscar um usuário pela chave/token de autenticação
    Optional<Usuario> findByChave(String token);
}