package school.sptech;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/destinos")
public class DestinoController {

    private final DestinoRepository repository;

    private static final String[] ESTADOS_VALIDOS = {
            "AC", "AL", "AP", "AM", "BA", "CE", "DF",
            "ES", "GO", "MA", "MT", "MS", "MG", "PA",
            "PB", "PR", "PE", "PI", "RJ", "RN", "RS",
            "RO", "RR", "SC", "SP", "SE", "TO"
    };

    public DestinoController(DestinoRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public ResponseEntity<List<Destino>> listar() {
        List<Destino> destinos = repository.listarTodos();

        return ResponseEntity.status(200).body(destinos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(@PathVariable Long id) {
        Destino destino = repository.buscarPorId(id);

        if (destino == null) {
            Map<String, String> resposta = Map.of(
                    "mensagem",
                    "Destino nao encontrado"
            );

            return ResponseEntity.status(404).body(resposta);
        }

        return ResponseEntity.status(200).body(destino);
    }

    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody Destino destino) {
        if (destino.getEstado() != null) {
            destino.setEstado(destino.getEstado().toUpperCase());
        }

        List<String> erros = validar(destino);

        if (!erros.isEmpty()) {
            Map<String, Object> resposta = Map.of(
                    "mensagem", "Dados invalidos",
                    "erros", erros
            );

            return ResponseEntity.status(400).body(resposta);
        }

        repository.salvar(destino);

        Map<String, String> resposta = Map.of(
                "mensagem",
                "Destino cadastrado com sucesso"
        );

        return ResponseEntity.status(201).body(resposta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(
            @PathVariable Long id,
            @RequestBody Destino destino
    ) {
        Destino destinoExistente = repository.buscarPorId(id);

        if (destinoExistente == null) {
            Map<String, String> resposta = Map.of(
                    "mensagem",
                    "Destino nao encontrado"
            );

            return ResponseEntity.status(404).body(resposta);
        }

        if (destino.getEstado() != null) {
            destino.setEstado(destino.getEstado().toUpperCase());
        }

        List<String> erros = validar(destino);

        if (!erros.isEmpty()) {
            Map<String, Object> resposta = Map.of(
                    "mensagem", "Dados invalidos",
                    "erros", erros
            );

            return ResponseEntity.status(400).body(resposta);
        }

        repository.atualizar(id, destino);
        destino.setId(id);

        return ResponseEntity.status(200).body(destino);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluir(@PathVariable Long id) {
        Destino destino = repository.buscarPorId(id);

        if (destino == null) {
            Map<String, String> resposta = Map.of(
                    "mensagem",
                    "Destino nao encontrado"
            );

            return ResponseEntity.status(404).body(resposta);
        }

        repository.excluir(id);

        return ResponseEntity.status(204).body(null);
    }

    private List<String> validar(Destino destino) {
        List<String> erros = new ArrayList<>();

        if (destino.getNome() == null || destino.getNome().length() < 3) {
            erros.add("Nome deve ter pelo menos 3 caracteres");
        }

        if (destino.getCidade() == null || destino.getCidade().isBlank()) {
            erros.add("Cidade e obrigatoria");
        }

        if (!estadoExiste(destino.getEstado())) {
            erros.add("Selecione um estado brasileiro valido");
        }

        if (destino.getTipo() == null || destino.getTipo().isBlank()) {
            erros.add("Tipo e obrigatorio");
        }

        if (destino.getPreco() == null || destino.getPreco() <= 0) {
            erros.add("Preco deve ser maior que zero");
        }

        if (destino.getDias() == null || destino.getDias() < 1) {
            erros.add("Dias deve ser maior que zero");
        }

        return erros;
    }

    private boolean estadoExiste(String estado) {
        if (estado == null) {
            return false;
        }

        for (String estadoValido : ESTADOS_VALIDOS) {
            if (estadoValido.equals(estado)) {
                return true;
            }
        }

        return false;
    }
}
