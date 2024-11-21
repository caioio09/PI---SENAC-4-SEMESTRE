document.addEventListener("DOMContentLoaded", async function () {
  const form = document.getElementById("addProductForm");

  // Verifica se estamos no modo de edição com base nos parâmetros da URL
  const urlParams = new URLSearchParams(window.location.search);
  const productId = urlParams.get("id");
  const isEditMode = productId !== null;

  if (isEditMode) {
    // Altera o título e o botão do formulário para indicar modo de edição
    const formTitle = document.getElementById("formTitle");
    if (formTitle) {
      formTitle.textContent = "Editar Produto";
    }
    const submitButton = document.getElementById("submitButton");
    if (submitButton) {
      submitButton.textContent = "Salvar Alterações";
    }

    // Preenche o campo "Código do Produto" com o ID e o desabilita para edição
    const idField = document.getElementById("id"); 
    if (idField) {
      idField.value = productId;
      idField.readOnly = true; // Define como somente leitura
      idField.style.backgroundColor = "#f0f0f0"; // Estiliza para parecer desabilitado
    }

    // Carrega os dados do produto para preencher o formulário
    try {
      const response = await fetch(`/produtos/${productId}`);
      if (response.ok) {
        const product = await response.json();
        // Preenche os campos do formulário com os dados do produto
        document.getElementById("nome").value = product.nome;
        document.getElementById("preco").value = product.preco;
        document.getElementById("descricao").value = product.descricao;
        document.getElementById("categoria_id").value = product.categoria.id;
      } else {
        alert("Erro ao carregar os dados do produto.");
      }
    } catch (error) {
      console.error("Erro ao carregar os dados do produto:", error);
      alert("Erro de conexão. Tente novamente mais tarde.");
    }
  }

  form.addEventListener("submit", async function (event) {
    event.preventDefault();

    // Captura os dados do formulário
    const formData = {
      id: productId || undefined, // Inclui o ID para edição, se disponível
      nome: document.getElementById("nome").value,
      preco: parseFloat(document.getElementById("preco").value),
      descricao: document.getElementById("descricao").value,
      quantidade: 0,
      categoria: {
        id: parseInt(document.getElementById("categoria_id").value)
      }
    };

    const url = isEditMode
      ? `/produtos/${productId}` // URL para editar o produto
      : "/produtos/produto";     // URL para criar novo produto
    const method = isEditMode ? "PUT" : "POST";

    try {
      const response = await fetch(url, {
        method: method,
        headers: {
          "Content-Type": "application/json"
        },
        body: JSON.stringify(formData)
      });

      if (response.ok) {
        alert(isEditMode ? "Produto atualizado com sucesso!" : "Produto cadastrado com sucesso!");
        window.location.href = "/estoque.html"; // Redireciona após o sucesso
      } else {
        const errorData = await response.json();
        alert("Erro ao salvar o produto: " + errorData.message);
      }
    } catch (error) {
      console.error("Erro no envio do formulário:", error);
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
