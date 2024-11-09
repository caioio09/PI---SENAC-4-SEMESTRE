document.addEventListener("DOMContentLoaded", function () {
  const form = document.getElementById("addProductForm");

  form.addEventListener("submit", async function (event) {
    event.preventDefault(); // Impede o envio padrão do formulário

    // Captura os dados do formulário
    const formData = {
      id: document.getElementById("id").value,
      nome: document.getElementById("nome").value,
      preco: parseFloat(document.getElementById("preco").value),
      descricao: document.getElementById("descricao").value,
      quantidade: 0,
      categoria: {
        id: parseInt(document.getElementById("categoria_id").value)
      }
    };

    try {
      // Envia os dados para o backend usando fetch
      const response = await fetch("/produtos/produto", {
    method: "POST",
    headers: {
        "Content-Type": "application/json"
    },
    body: JSON.stringify(formData)
});

      if (response.ok) {
        alert("Produto cadastrado com sucesso!");
        window.location.href = "/estoque.html";  // Redireciona para a página inicial após o alerta
      } else {
        const errorData = await response.json();
        alert("Erro ao cadastrar produto: " + errorData.message); // Exibe a mensagem de erro
      }
    } catch (error) {
      console.error("Erro no envio do formulário:", error);
      alert("Erro de conexão. Tente novamente mais tarde.");
    }
  });
});
