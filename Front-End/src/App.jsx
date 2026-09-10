import { useEffect, useState } from "react";
import Formulario from "./Formulario";
import Lista from "./Lista";
import {
  cadastrarDestino,
  excluirDestino,
  listarDestinos
} from "./api";
import styles from "./App.module.css";

export default function App() {
  const [destinos, setDestinos] = useState([]);
  const [carregando, setCarregando] = useState(false);
  const [mensagem, setMensagem] = useState("");
  const [erro, setErro] = useState("");

  async function carregar() {
    setCarregando(true);
    setErro("");

    try {
      const lista = await listarDestinos();
      setDestinos(lista);
    } catch (e) {
      setErro("Falha ao conectar. Ligue a API na porta 8080.");
    }

    setCarregando(false);
  }

  useEffect(() => {
    carregar();
  }, []);

  async function salvar(destino) {
    setErro("");
    setMensagem("");

    try {
      await cadastrarDestino(destino);
      setMensagem("Destino cadastrado com sucesso!");
      await carregar();
      return true;
    } catch (e) {
      setErro(e.message);
      return false;
    }
  }

  async function excluir(id) {
    await excluirDestino(id);
    setMensagem("Destino excluido com sucesso!");
    carregar();
  }

  return (
    <div className={styles.pagina}>
      <header className={styles.cabecalho}>
        <h1>Turismo Brasil</h1>
        <p>Cadastro e consulta de destinos turisticos</p>
      </header>

      <main className={styles.conteudo}>
        <Formulario aoSalvar={salvar} />

        {mensagem && (
          <p className={styles.sucesso}>{mensagem}</p>
        )}

        {erro && (
          <p className={styles.erro}>{erro}</p>
        )}

        <section className={styles.caixa}>
          <h2>Destinos cadastrados</h2>

          {carregando ? (
            <p>Carregando...</p>
          ) : (
            <Lista destinos={destinos} aoExcluir={excluir} />
          )}
        </section>
      </main>
    </div>
  );
}
