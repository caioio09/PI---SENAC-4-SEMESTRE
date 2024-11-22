document.addEventListener("DOMContentLoaded", async function () {
  const produtoSelect = document.getElementById("produtoId");

  // Carrega a lista de produtos para o dropdown
  try {
    const response = await fetch("/produtos");
    if (response.ok) {
      const produtos = await response.json();
      produtos.forEach((produto) => {
        const option = document.createElement("option");
        option.value = produto.id;
        option.textContent = `${produto.nome} (ID: ${produto.id})`;
        produtoSelect.appendChild(option);
      });
    } else {
      alert("Erro ao carregar produtos.");
    }
  } catch (error) {
    console.error("Erro de conexão ao carregar produtos:", error);
    alert("Erro de conexão. Tente novamente mais tarde.");
  }

  // Processa a saída de estoque
  document.getElementById("saidaEstoqueForm").addEventListener("submit", async function (event) {
    event.preventDefault();

    const formData = {
      produtoId: parseInt(produtoSelect.value),
      quantidade: parseInt(document.getElementById("quantidade").value),
      dataOperacao: new Date().toISOString(), // Data atual no formato ISO
      tipoOperacao: "SAIDA", // Define que é uma saída
      fornecedorOuDestinatario: document.getElementById("destino").value, // Para saída, usamos "destino"
    };

    try {
      const response = await fetch("/estoque/saida", {
        method: "POST", // POST é usado para criar uma nova saída
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(formData),
      });

      if (response.ok) {
        alert("Saída de estoque registrada com sucesso!");
        window.location.href = "/estoque.html"; // Redireciona após o sucesso
      } else {
        const errorData = await response.json();
        alert("Erro ao registrar saída: " + (errorData.message || "Erro desconhecido"));
      }
    } catch (error) {
      console.error("Erro ao registrar saída:", error);
      alert("Erro de conexão. Tente novamente mais tarde.");
    }
  });
});

// Adiciona funcionalidade ao link "Sair"
document.querySelector('a[href="#logout"]').addEventListener("click", function (event) {
  event.preventDefault(); // Evita o comportamento padrão

  // Faz a requisição POST para o backend
  fetch("/login/logout", {
    method: "POST",
  })
    .then((response) => {
      if (response.ok) {
        alert("Sua sessão foi encerrada.");
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