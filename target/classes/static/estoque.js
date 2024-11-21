async function renderProductTable() {
  const tableBody = document.querySelector("#productTable tbody");
  tableBody.innerHTML = "";

  try {
    const response = await fetch("http://localhost:8080/produtos");
    const products = await response.json();

    products.forEach((product) => {
      const row = document.createElement("tr");
      row.innerHTML = `
        <td>${product.id}</td>
        <td>${product.nome}</td>
        <td>${product.quantidade}</td>
        <td>${product.preco.toFixed(2)}</td>
        <td>${product.descricao}</td>
        <td>${product.categoria.nome}</td>
        <td class="actions">
          <button class="edit-btn" onclick="editProduct(${product.id})">Editar</button>
          <button class="delete-btn" onclick="deleteProduct(${product.id}, '${product.nome}')">Excluir</button>
        </td>
      `;
      tableBody.appendChild(row);
    });
  } catch (error) {
    console.error("Erro ao carregar produtos:", error);
    alert("Erro de conexão. Tente novamente mais tarde.");
  }
}

function editProduct(productId) {
  // Redireciona para a página de cadastro com o ID do produto na URL
  window.location.href = `/adiciona.html?id=${productId}`;
}

async function deleteProduct(id, nome) {
  if (confirm(`Tem certeza que deseja excluir o produto ${nome} (ID: ${id})?`)) {
    try {
      const response = await fetch(`http://localhost:8080/produtos/${id}`, {
        method: "DELETE",
      });

      if (response.ok) {
        alert("Produto excluído com sucesso!");
        renderProductTable();
      } else {
        alert("Erro ao excluir o produto.");
      }
    } catch (error) {
      console.error("Erro ao excluir o produto:", error);
      alert("Erro de conexão. Tente novamente mais tarde.");
    }
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
        if (dashboardLink) {
          dashboardLink.parentElement.remove();
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


async function renderProductTable(filter = "") {
  const tableBody = document.querySelector("#productTable tbody");
  tableBody.innerHTML = "";

  try {
    // Define a URL: com ou sem o filtro
    const url = filter
      ? `http://localhost:8080/produtos/filtrar?filtro=${encodeURIComponent(filter)}`
      : "http://localhost:8080/produtos";

    const response = await fetch(url);
    const products = await response.json();

    // Verifica se há resultados
    if (products.length === 0) {
      tableBody.innerHTML = "<tr><td colspan='7'>Nenhum produto encontrado.</td></tr>";
      return;
    }

    // Preenche a tabela com os produtos
    products.forEach((product) => {
      const row = document.createElement("tr");
      row.innerHTML = `
        <td>${product.id}</td>
        <td>${product.nome}</td>
        <td>${product.quantidade}</td>
        <td>${product.preco.toFixed(2)}</td>
        <td>${product.descricao}</td>
        <td>${product.categoria ? product.categoria.nome : "Sem Categoria"}</td>
        <td class="actions">
          <button class="edit-btn" onclick="editProduct(${product.id})">Editar</button>
          <button class="delete-btn" onclick="deleteProduct(${product.id}, '${product.nome}')">Excluir</button>
        </td>
      `;
      tableBody.appendChild(row);
    });
  } catch (error) {
    console.error("Erro ao carregar produtos:", error);
    alert("Erro de conexão. Tente novamente mais tarde.");
  }
}

// Função para ativar a filtragem
document.getElementById("filterButton").addEventListener("click", () => {
  const filterValue = document.getElementById("filterInput").value.trim();
  renderProductTable(filterValue);
});

// Função para limpar o filtro e recarregar todos os produtos
document.getElementById("clearFilterButton").addEventListener("click", () => {
  document.getElementById("filterInput").value = "";
  renderProductTable();
});

// Evento para acionar o filtro com a tecla Enter
document.getElementById("filterInput").addEventListener("keypress", (event) => {
  if (event.key === "Enter") {
    event.preventDefault(); // Previne o comportamento padrão
    document.getElementById("filterButton").click(); // Simula o clique no botão de filtro
  }
});

// Carrega todos os produtos ao inicializar
renderProductTable();
