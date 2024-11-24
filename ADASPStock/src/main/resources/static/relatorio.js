document.addEventListener("DOMContentLoaded", () => {
    carregarRelatorio();
  });
  
  // Função para buscar os dados do relatório
  async function carregarRelatorio() {
    try {
      // Chamar a API para obter as entradas e saídas
      const [entradasResponse, saidasResponse] = await Promise.all([
        fetch("/estoque/entradas"),
        fetch("/estoque/saidas"),
      ]);
  
      if (!entradasResponse.ok || !saidasResponse.ok) {
        throw new Error("Erro ao carregar os dados do relatório.");
      }
  
      const entradas = await entradasResponse.json();
      const saidas = await saidasResponse.json();
  
      // Combina as entradas e saídas em um único array
      const movimentacoes = [
        ...entradas.map((entrada) => ({ ...entrada, tipo: "Entrada" })),
        ...saidas.map((saida) => ({ ...saida, tipo: "Saída" })),
      ];
  
      // Ordenar as movimentações por data (opcional)
      movimentacoes.sort(
        (a, b) => new Date(a.dataEntrada) - new Date(b.dataEntrada)
      );
  
      // Renderizar as movimentações na tabela
      renderizarTabela(movimentacoes);
    } catch (error) {
      console.error("Erro ao carregar o relatório:", error);
    }

    function renderizarTabela(movimentacoes) {
      const tabelaCorpo = document.querySelector("#productTable tbody");
      tabelaCorpo.innerHTML = ""; // Limpa os dados existentes na tabela
    
      // Combinar entradas e saídas em uma lista única
      const movimentacoesOrdenadas = movimentacoes.sort((a, b) => {
        // Comparar as datas para ordenar por mais antiga para mais recente
        const dataA = new Date(a.tipo === "Entrada" ? a.dataEntrada : a.dataSaida);
        const dataB = new Date(b.tipo === "Entrada" ? b.dataEntrada : b.dataSaida);
        return dataA - dataB;
      });
    
      // Itera sobre a lista ordenada para gerar as linhas da tabela
      movimentacoesOrdenadas.forEach((mov) => {
        const total = calcularTotal(mov.quantidade, mov.precoUnitario);
        const dataMovimento = mov.tipo === "Entrada" ? mov.dataEntrada : mov.dataSaida;
    
        // Define a classe com base no tipo de movimento
        const tipoClasse = mov.tipo === "Entrada" ? "entrada" : "saida";
        const linhaClasse = mov.tipo === "Entrada" ? "linha-entrada" : "linha-saida";
    
        const linha = `
          <tr class="${linhaClasse}">
            <td>${mov.produtoId}</td>
            <td>${mov.produtoNome}</td>
            <td>${mov.quantidade}</td>
            <td>${formatarPreco(mov.precoUnitario)}</td>
            <td>${formatarPreco(total)}</td>
            <td>${mov.tipo === "Entrada" ? mov.fornecedor : mov.destino || "N/A"}</td>
            <td class="${tipoClasse}">${mov.tipo}</td>
            <td>${formatarData(dataMovimento)}</td>
          </tr>
        `;
    
        tabelaCorpo.insertAdjacentHTML("beforeend", linha);
      });
    }
  
  // Função para calcular o total (quantidade * preço unitário)
  function calcularTotal(quantidade, precoUnitario) {
    return quantidade * precoUnitario;
  }
  
  // Função para formatar o preço em R$ (BRL)
  function formatarPreco(preco) {
    return preco.toLocaleString("pt-BR", { style: "currency", currency: "BRL" });
  }
  
  // Função para formatar a data
  function formatarData(data) {
    const dataObj = new Date(data);
    return dataObj.toLocaleDateString("pt-BR", {
      day: "2-digit",
      month: "2-digit",
      year: "numeric",
      hour: "2-digit",
      minute: "2-digit",
    });
  }
} 
  
// Adiciona funcionalidade ao link "Sair"
document.querySelector('a[href="#logout"]').addEventListener("click", function (event) {
    event.preventDefault(); // Evita o comportamento padrão
  
    // Faz a requisição POST para o backend
    fetch("/login/logout", {
      method: "POST",
    })
      .then((response) => {
        if (response.ok) {
          // Redireciona para a página de login após o logout
          alert("Sua sessão foi encerrada")
          window.location.href = "index.html";
        } else {
          alert("Erro ao realizar logout. Tente novamente.");
        }
      })
      .catch((error) => {
        console.error("Erro na requisição de logout:", error);
        alert("Erro ao conectar ao servidor. Tente novamente mais tarde.");
      });
  });
  
  document.addEventListener("DOMContentLoaded", () => {
    // Faz uma requisição para obter o usuário logado
    fetch("/login/usuario-logado")
      .then((response) => {
        if (!response.ok) {
          throw new Error("Erro ao obter usuário logado.");
        }
        return response.json();
      })
      .then((data) => {
        // Atualiza os elementos no HTML com os dados do usuário
        document.getElementById("usuario").textContent = `Usuário: ${data.username}`;
        document.getElementById("cargo").textContent = `Cargo: ${data.cargo}`;
      })
      .catch((error) => {
        console.error("Erro:", error);
        alert("Não foi possível carregar os dados do usuário logado.");
      });
  });
  
  document.addEventListener("DOMContentLoaded", () => {
    // Verifica o cargo do usuário logado
    fetch("/login/verificar-cargo")
      .then((response) => {
        if (!response.ok) {
          throw new Error("Erro ao verificar cargo.");
        }
        return response.text(); // Retorna o cargo do usuário
      })
      .then((cargo) => {
        if (cargo !== "Gerente") {
          // Remove o link do Dashboard se não for gerente
          const dashboardLink = document.querySelector('a[href="dashboard.html"]');
          const relatorio = document.querySelector('a[href="relatorio.html"]');
          if (dashboardLink && relatorio) {
            dashboardLink.parentElement.remove();
            relatorio.parentElement.remove();
          }
        }
      })
      .catch((error) => {
        console.error("Erro:", error);
      });
  });
  
  document.addEventListener("DOMContentLoaded", () => {
    // Verifica se há um usuário logado
    fetch("/login/verificar-usuario-logado")
      .then((response) => {
        if (!response.ok) {
          throw new Error("Erro ao verificar usuário logado.");
        }
        return response.json(); // Retorna true ou false
      })
      .then((isLoggedIn) => {
        if (!isLoggedIn) {
          // Redireciona para index.html se não houver usuário logado
          alert("Você não está logado. Por favor, faça login.");
          window.location.href = "index.html";
        }
      })
      .catch((error) => {
        console.error("Erro:", error);
        alert("Erro ao verificar sessão. Retornando para o login.");
        window.location.href = "index.html";
      });
  });
  