import styles from "./Lista.module.css";

export default function Lista({ destinos, aoExcluir }) {
  if (destinos.length === 0) {
    return <p>Nenhum destino cadastrado.</p>;
  }

  return (
    <div className={styles.grade}>
      {destinos.map((destino) => (
        <article className={styles.cartao} key={destino.id}>
          <span>{destino.tipo}</span>
          <h3>{destino.nome}</h3>
          <p>{destino.cidade} - {destino.estado}</p>
          <p>{destino.descricao}</p>

          <div className={styles.informacoes}>
            <strong>R$ {destino.preco.toFixed(2)}</strong>
            <b>{destino.dias} dia(s)</b>
          </div>

          <button onClick={() => aoExcluir(destino.id)}>
            Excluir
          </button>
        </article>
      ))}
    </div>
  );
}
