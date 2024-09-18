package br.edu.ifpr.compracomida.controller;

import br.edu.ifpr.compracomida.model.Categoria;
import br.edu.ifpr.compracomida.repositorio.CategoriaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // Define que esta classe é um controlador REST
@RequestMapping("/categorias") // Define o endpoint base para categorias
public class CategoriaController {

    @Autowired
    private CategoriaRepositorio categoriaRepositorio; // Injeção do repositório de categorias

    // Método GET para listar todas as categorias
    @GetMapping
    public ResponseEntity<List<Categoria>> listarCategorias() {
        List<Categoria> categorias = categoriaRepositorio.findAll(); // Busca todas as categorias no banco
        return ResponseEntity.ok(categorias); // Retorna a lista de categorias com status 200 OK
    }

    // Método POST para criar uma nova categoria
    @PostMapping
    public ResponseEntity<?> criarCategoria(@RequestBody Categoria categoria) {
        try {
            // Tenta salvar a nova categoria no banco
            Categoria novaCategoria = categoriaRepositorio.save(categoria);
            // Retorna a categoria recém-criada com status 201 Created
            return ResponseEntity.status(HttpStatus.CREATED).body(novaCategoria);
        } catch (Exception e) {
            // Retorna status 400 Bad Request em caso de erro ao criar a categoria
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao criar categoria: " + e.getMessage());
        }
    }
}
