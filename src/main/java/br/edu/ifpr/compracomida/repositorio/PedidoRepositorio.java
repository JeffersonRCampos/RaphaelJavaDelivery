package br.edu.ifpr.compracomida.repositorio;

import br.edu.ifpr.compracomida.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // Indica que esta interface é um repositório Spring para a entidade Pedido
public interface PedidoRepositorio extends JpaRepository<Pedido, Long> {
    // Esta interface herda operações padrão de JpaRepository como salvar, deletar e buscar por ID
}