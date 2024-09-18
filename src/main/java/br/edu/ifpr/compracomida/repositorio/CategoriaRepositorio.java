package br.edu.ifpr.compracomida.repositorio;

import br.edu.ifpr.compracomida.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // Indica que esta interface é um repositório Spring para a entidade Categoria
public interface CategoriaRepositorio extends JpaRepository<Categoria, Long> {
    // Esta interface herda operações padrão de JpaRepository como salvar, deletar e buscar por ID
}