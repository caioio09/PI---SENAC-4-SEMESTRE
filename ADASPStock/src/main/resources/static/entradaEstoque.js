document.addEventListener("DOMContentLoaded", async function () {
    const produtoSelect = document.getElementById("produtoId");
  
    // Carrega a lista de produtos para o dropdown
    try {
      const response = await fetch("/produtos");
      if (response.ok) {
        const produtos = await response.json();
        produtos.forEach(produto => {
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
  
    // Processa a entrada de estoque
    document.getElementById("entradaEstoqueForm").addEventListener("submit", async function (event) {
      event.preventDefault();
  
      const formData = {
        produtoId: parseInt(produtoSelect.value),
        quantidade: parseInt(document.getElementById("quantidade").value),
        dataEntrada: new Date().toISOString(),  // Gera a data atual no formato ISO
        fornecedor: document.getElementById("fornecedor").value
      };
  
      try {
        const response = await fetch("/estoque/entrada", {
          method: "POST",
          headers: {
            "Content-Type": "application/json"
          },
          body: JSON.stringify(formData)
        });
  
        if (response.ok) {
          alert("Entrada de estoque registrada com sucesso!");
          window.location.href = "/estoque.html";  // Redireciona após o sucesso
        } else {
          const errorData = await response.json();
          alert("Erro ao registrar entrada: " + errorData.message);
        }
      } catch (error) {
        console.error("Erro ao registrar entrada:", error);
        alert("Erro de conexão. Tente novamente mais tarde.");
      }
    });
  });
  