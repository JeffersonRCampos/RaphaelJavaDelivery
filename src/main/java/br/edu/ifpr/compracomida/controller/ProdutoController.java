package br.edu.ifpr.compracomida.controller;

import br.edu.ifpr.compracomida.model.Produto;
import br.edu.ifpr.compracomida.repositorio.ProdutoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoRepositorio produtoRepositorio; // Injeção do repositório de produtos

    // Método GET para listar todos os produtos
    @GetMapping
    public ResponseEntity<List<Produto>> listarProdutos() {
        List<Produto> produtos = produtoRepositorio.findAll(); // Busca todos os produtos no banco
        return ResponseEntity.ok(produtos); // Retorna a lista de produtos com status 200 OK
    }

    // Método POST para criar um novo produto
    @PostMapping
    public ResponseEntity<?> criarProduto(@RequestBody Produto produto) {
        try {
            Produto novoProduto = produtoRepositorio.save(produto); // Salva o novo produto no banco
            return ResponseEntity.status(HttpStatus.CREATED).body(novoProduto); // Retorna o produto recém-criado com status 201 Created
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao criar produto: " + e.getMessage());
        }
    }
}