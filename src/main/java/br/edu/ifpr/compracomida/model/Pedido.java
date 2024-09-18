package br.edu.ifpr.compracomida.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // ID do pedido, gerado automaticamente

    @Column(nullable = false)
    private LocalDateTime data; // Data do pedido, obrigatório

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario; // Relação Many-to-One: Pedido pertence a um Usuário

    @Column(nullable = false)
    private float valor; // Valor total do pedido, obrigatório

    @Column(nullable = false)
    private String entrega; // Endereço de entrega, obrigatório

    @ManyToMany
    @JoinTable(
        name = "pedido_produto", // Nome da tabela intermediária
        joinColumns = @JoinColumn(name = "pedido_id"), // Chave estrangeira que referencia Pedido
        inverseJoinColumns = @JoinColumn(name = "produto_id") // Chave estrangeira que referencia Produto
    )
    private List<Produto> produtos; // Lista de produtos no pedido
}