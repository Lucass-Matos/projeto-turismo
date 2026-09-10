import { useState } from "react";
import { ESTADOS } from "./estados";
import styles from "./Formulario.module.css";

const VAZIO = {
  nome: "",
  cidade: "",
  estado: "",
  tipo: "Praia",
  preco: "",
  dias: "",
  descricao: ""
};

export default function Formulario({ aoSalvar }) {
  const [formulario, setFormulario] = useState(VAZIO);
  const [erro, setErro] = useState("");

  function alterar(evento) {
    setFormulario({
      ...formulario,
      [evento.target.name]: evento.target.value
    });
  }

  async function enviar(evento) {
    evento.preventDefault();
    setErro("");

    const preco = Number(formulario.preco.replace(",", "."));

    if (isNaN(preco) || preco <= 0) {
      setErro("Digite um preco valido, como 1200,50");
      return;
    }

    const destino = {
      ...formulario,
      preco: preco,
      dias: Number(formulario.dias)
    };

    const salvou = await aoSalvar(destino);

    if (salvou) {
      setFormulario(VAZIO);
    }
  }

  return (
    <section className={styles.caixa}>
      <h2>Novo destino</h2>

      <form onSubmit={enviar}>
        <div className={styles.grade}>
          <label>
            Nome
            <input
              required
              name="nome"
              value={formulario.nome}
              onChange={alterar}
            />
          </label>

          <label>
            Cidade
            <input
              required
              name="cidade"
              value={formulario.cidade}
              onChange={alterar}
            />
          </label>

          <label>
            Estado
            <select
              required
              name="estado"
              value={formulario.estado}
              onChange={alterar}
            >
              <option value="">Selecione</option>

              {ESTADOS.map((estado) => (
                <option key={estado} value={estado}>
                  {estado}
                </option>
              ))}
            </select>
          </label>

          <label>
            Tipo
            <select
              name="tipo"
              value={formulario.tipo}
              onChange={alterar}
            >
              <option>Praia</option>
              <option>Montanha</option>
              <option>Cidade Historica</option>
              <option>Ecoturismo</option>
              <option>Aventura</option>
            </select>
          </label>

          <label>
            Preco
            <input
              required
              name="preco"
              value={formulario.preco}
              onChange={alterar}
              placeholder="Exemplo: 1200,50"
            />
            <small>Use virgula ou ponto para os centavos.</small>
          </label>

          <label>
            Dias
            <input
              required
              type="number"
              min="1"
              name="dias"
              value={formulario.dias}
              onChange={alterar}
            />
          </label>
        </div>

        <label className={styles.descricao}>
          Descricao
          <textarea
            name="descricao"
            value={formulario.descricao}
            onChange={alterar}
          />
        </label>

        {erro && (
          <p className={styles.erro}>{erro}</p>
        )}

        <button>Cadastrar destino</button>
      </form>
    </section>
  );
}
