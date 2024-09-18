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
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // ID da categoria, gerado automaticamente

    @Column(nullable = false, unique = true)
    private String nome; // Nome da categoria, obrigatório e único
}