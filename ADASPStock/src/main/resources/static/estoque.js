const products = [
    { code: "P001", name: "Notebook", quantity: 10, price: 2500.0 },
    { code: "P002", name: "Smartphone", quantity: 15, price: 1200.0 },
    { code: "P003", name: "Tablet", quantity: 8, price: 800.0 },
    { code: "P004", name: "Mouse", quantity: 30, price: 50.0 },
    { code: "P005", name: "Teclado", quantity: 25, price: 100.0 },
  ];

  function renderProductTable() {
    const tableBody = document.querySelector("#productTable tbody");
    tableBody.innerHTML = "";

    products.forEach((product) => {
      const row = document.createElement("tr");
      row.innerHTML = `
                    <td>${product.code}</td>
                    <td>${product.name}</td>
                    <td>${product.quantity}</td>
                    <td>${product.price.toFixed(2)}</td>
                    <td class="actions">
                        <button class="edit-btn" onclick="editProduct('${
                          product.code
                        }')">Editar</button>
                        <button class="delete-btn" onclick="deleteProduct('${
                          product.code
                        }')">Excluir</button>
                    </td>
                `;
      tableBody.appendChild(row);
    });
  }

  function editProduct(code) {
    console.log("Editando produto:", code);

    alert(
      "Funcionalidade de edição a ser implementada para o produto: " + code
    );
  }

  function deleteProduct(code) {
    console.log("Excluindo produto:", code);

    const index = products.findIndex((p) => p.code === code);
    if (index > -1) {
      products.splice(index, 1);
      renderProductTable();
      alert("Produto excluído com sucesso!");
    }
  }

  document
    .querySelector(".search-bar")
    .addEventListener("keyup", function (e) {
      if (e.key === "Enter") {
        const searchTerm = this.value.toLowerCase();
        const filteredProducts = products.filter(
          (product) =>
            product.name.toLowerCase().includes(searchTerm) ||
            product.code.toLowerCase().includes(searchTerm)
        );
        renderFilteredProducts(filteredProducts);
        console.log("Busca realizada por:", searchTerm);
      }
    });

  function renderFilteredProducts(filteredProducts) {
    const tableBody = document.querySelector("#productTable tbody");
    tableBody.innerHTML = "";

    filteredProducts.forEach((product) => {
      const row = document.createElement("tr");
      row.innerHTML = `
                    <td>${product.code}</td>
                    <td>${product.name}</td>
                    <td>${product.quantity}</td>
                    <td>${product.price.toFixed(2)}</td>
                    <td class="actions">
                        <button class="edit-btn" onclick="editProduct('${
                          product.code
                        }')">Editar</button>
                        <button class="delete-btn" onclick="deleteProduct('${
                          product.code
                        }')">Excluir</button>
                    </td>
                `;
      tableBody.appendChild(row);
    });
  }

  renderProductTable();