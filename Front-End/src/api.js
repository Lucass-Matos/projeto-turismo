const URL = "http://localhost:8080/destinos";

export async function listarDestinos() {
  const resposta = await fetch(URL);

  if (!resposta.ok) {
    throw new Error("Erro ao buscar destinos");
  }

  return resposta.json();
}

export async function cadastrarDestino(destino) {
  const resposta = await fetch(URL, {
    method: "POST",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify(destino)
  });

  const dados = await resposta.json();

  if (!resposta.ok) {
    throw new Error(dados.erros.join(" / "));
  }

  return dados;
}

export async function excluirDestino(id) {
  const resposta = await fetch(`${URL}/${id}`, {
    method: "DELETE"
  });

  if (!resposta.ok) {
    throw new Error("Erro ao excluir destino");
  }
}
