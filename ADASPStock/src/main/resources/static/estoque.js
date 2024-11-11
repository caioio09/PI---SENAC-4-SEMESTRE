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

renderProductTable();
