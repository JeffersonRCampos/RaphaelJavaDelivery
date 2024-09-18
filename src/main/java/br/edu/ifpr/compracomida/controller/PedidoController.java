package br.edu.ifpr.compracomida.controller;

import br.edu.ifpr.compracomida.model.*;
import br.edu.ifpr.compracomida.repositorio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoRepositorio pedidoRepositorio;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private ProdutoRepositorio produtoRepositorio;

    // Método POST para criar um novo pedido
    @PostMapping
    public ResponseEntity<?> criarPedido(@RequestBody Pedido pedido) {
        try {
            // Verifica se o endereço de entrega foi fornecido
            if (pedido.getEntrega() == null || pedido.getEntrega().isEmpty()) {
                return ResponseEntity.badRequest().body("Endereço de entrega é obrigatório.");
            }

            // Busca o usuário associado ao pedido
            Usuario usuario = usuarioRepositorio.findById(pedido.getUsuario().getId())
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

            // Busca os produtos associados ao pedido
            List<Produto> produtos = produtoRepositorio.findAllById(
                    pedido.getProdutos().stream().map(Produto::getId).toList());

            // Configura o usuário e os produtos no pedido
            pedido.setUsuario(usuario);
            pedido.setProdutos(produtos);

            // Salva o novo pedido no banco de dados
            Pedido novoPedido = pedidoRepositorio.save(pedido);

            return ResponseEntity.status(HttpStatus.CREATED).body(novoPedido); // Retorna o pedido com status 201 Created
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao criar o pedido: " + e.getMessage());
        }
    }

    // Método GET para listar todos os pedidos
    @GetMapping
    public ResponseEntity<List<Pedido>> listarPedidos() {
        List<Pedido> pedidos = pedidoRepositorio.findAll(); // Busca todos os pedidos no banco
        return ResponseEntity.ok(pedidos); // Retorna a lista de pedidos com status 200 OK
    }

    // Método GET para buscar um pedido específico pelo ID
    @GetMapping("/{id}")
    public ResponseEntity<?> obterPedido(@PathVariable Long id) {
        try {
            Pedido pedido = pedidoRepositorio.findById(id)
                    .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
            return ResponseEntity.ok(pedido); // Retorna o pedido com status 200 OK
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pedido não encontrado");
        }
    }
}