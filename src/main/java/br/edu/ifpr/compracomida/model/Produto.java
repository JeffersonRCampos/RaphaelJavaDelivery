package br.edu.ifpr.compracomida.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // ID do produto, gerado automaticamente

    @Column(nullable = false)
    private String nome; // Nome do produto, obrigatório

    @Column(nullable = false)
    private double valor; // Valor do produto, obrigatório

    @ManyToOne
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria; // Relação Many-to-One: Produto pertence a uma Categoria
}