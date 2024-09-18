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
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // ID do usuário, gerado automaticamente

    @Column(nullable = false)
    private String nome; // Nome do usuário, obrigatório

    @Column(nullable = false, unique = true)
    private String email; // Email do usuário, obrigatório e único

    @Column(nullable = false)
    private String senha; // Senha do usuário, obrigatória

    @Column(nullable = true)
    private String chave; // Chave/token para autenticação, pode ser nula
}