package br.edu.ifpr.compracomida.repositorio;

import br.edu.ifpr.compracomida.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // Indica que esta interface é um repositório Spring para a entidade Produto
public interface ProdutoRepositorio extends JpaRepository<Produto, Long> {
    // Esta interface herda operações padrão de JpaRepository como salvar, deletar e buscar por ID
}