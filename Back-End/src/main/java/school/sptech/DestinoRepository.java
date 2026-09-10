package school.sptech;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DestinoRepository {

    private final JdbcTemplate jdbcTemplate;

    public DestinoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Destino> mapper = (resultado, linha) -> {
        Destino destino = new Destino();
        destino.setId(resultado.getLong("id"));
        destino.setNome(resultado.getString("nome"));
        destino.setCidade(resultado.getString("cidade"));
        destino.setEstado(resultado.getString("estado"));
        destino.setTipo(resultado.getString("tipo"));
        destino.setPreco(resultado.getDouble("preco"));
        destino.setDias(resultado.getInt("dias"));
        destino.setDescricao(resultado.getString("descricao"));
        return destino;
    };

    public List<Destino> listarTodos() {
        String sql = "SELECT * FROM destino ORDER BY id DESC";
        return jdbcTemplate.query(sql, mapper);
    }

    public Destino buscarPorId(Long id) {
        String sql = "SELECT * FROM destino WHERE id = ?";
        List<Destino> lista = jdbcTemplate.query(sql, mapper, id);

        if (lista.isEmpty()) {
            return null;
        }

        return lista.get(0);
    }

    public void salvar(Destino destino) {
        String sql = "INSERT INTO destino "
                + "(nome, cidade, estado, tipo, preco, dias, descricao) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.update(
                sql,
                destino.getNome(),
                destino.getCidade(),
                destino.getEstado(),
                destino.getTipo(),
                destino.getPreco(),
                destino.getDias(),
                destino.getDescricao()
        );
    }

    public void atualizar(Long id, Destino destino) {
        String sql = "UPDATE destino SET nome = ?, cidade = ?, estado = ?, "
                + "tipo = ?, preco = ?, dias = ?, descricao = ? WHERE id = ?";

        jdbcTemplate.update(
                sql,
                destino.getNome(),
                destino.getCidade(),
                destino.getEstado(),
                destino.getTipo(),
                destino.getPreco(),
                destino.getDias(),
                destino.getDescricao(),
                id
        );
    }

    public void excluir(Long id) {
        String sql = "DELETE FROM destino WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
