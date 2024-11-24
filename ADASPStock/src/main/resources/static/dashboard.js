async function fetchProducts() {
  try {
    const response = await fetch("http://localhost:8080/produtos"); // Altere a URL para o endpoint real
    if (!response.ok) throw new Error("Erro ao carregar os produtos");
    const products = await response.json(); // Os dados devem estar no formato JSON
    return products;
  } catch (error) {
    console.error(error);
    return [];
  }
}

async function updateStatistics() {
  const products = await fetchProducts();

  function calculateTotalProducts() {
    return products.length;
  }

  function calculateTotalValue() {
    return products.reduce(
      (total, product) => total + product.quantidade * product.preco,
      0
    );
  }

  function calculateLowStock(threshold = 10) {
    return products.filter((product) => product.quantidade < threshold).length;
  }

  document.getElementById("totalProducts").textContent = calculateTotalProducts();
  document.getElementById("totalValue").textContent = `R$ ${calculateTotalValue().toFixed(2)}`;
  document.getElementById("lowStock").textContent = calculateLowStock();
}

updateStatistics();

async function renderTopSellingChart() {
  try {
    const response = await fetch("http://localhost:8080/estoque/mais-vendidos");
    if (!response.ok) throw new Error("Erro ao carregar os produtos mais vendidos");
    const data = await response.json();

    // Processar os dados retornados
    const labels = data.map(item => item[0]); // Nome dos produtos
    const values = data.map(item => item[1]); // Total vendido

    // Criar o gráfico
    const ctx = document.getElementById("topSellingChart").getContext("2d");
    new Chart(ctx, {
      type: "bar",
      data: {
        labels: labels,
        datasets: [
          {
            label: "Produtos Mais Vendidos",
            data: values,
            backgroundColor: "rgba(75, 192, 192, 0.5)",
            borderColor: "rgb(75, 192, 192)",
            borderWidth: 1,
          },
        ],
      },
      options: {
        responsive: true,
        maintainAspectRatio: false,
        scales: {
          y: {
            beginAtZero: true,
          },
        },
      },
    });
  } catch (error) {
    console.error(error);
  }
}

// Chamando a função
renderTopSellingChart();

async function renderStockDistributionChart() {
  try {
    const products = await fetchProducts();
    
    // Log para depuração
    console.log("Produtos recebidos:", products);

    // Verifique se 'products' é um array válido e contém produtos
    if (!Array.isArray(products) || products.length === 0) {
      throw new Error("Nenhum produto encontrado.");
    }

    // Mapear as categorias a partir dos produtos
    const categories = [
      ...new Set(
        products
          .map((p) => (p.categoria && p.categoria.nome ? p.categoria.nome : null)) // Acesse 'categoria.nome' corretamente
          .filter(Boolean) // Remove valores nulos ou indefinidos
      ),
    ];

    console.log("Categorias identificadas:", categories);

    if (categories.length === 0) {
      throw new Error("Nenhuma categoria encontrada nos produtos.");
    }

    // Calcular o estoque total por categoria
    const stockByCategory = categories.map((category) =>
      products
        .filter((p) => p.categoria && p.categoria.nome === category)
        .reduce((sum, p) => sum + (p.quantidade || 0), 0) // Garantir que quantidade seja válida
    );

    console.log("Estoque por categoria:", stockByCategory);

    // Criação do gráfico de pizza
    const ctx = document.getElementById("stockDistributionChart").getContext("2d");
    new Chart(ctx, {
      type: "pie",
      data: {
        labels: categories,
        datasets: [
          {
            data: stockByCategory,
            backgroundColor: [
              "rgba(255, 99, 132, 0.5)",
              "rgba(54, 162, 235, 0.5)",
              "rgba(255, 206, 86, 0.5)",
              "rgba(75, 192, 192, 0.5)",
              "rgba(153, 102, 255, 0.5)",
            ],
            borderColor: [
              "rgba(255, 99, 132, 1)",
              "rgba(54, 162, 235, 1)",
              "rgba(255, 206, 86, 1)",
              "rgba(75, 192, 192, 1)",
              "rgba(153, 102, 255, 1)",
            ],
            borderWidth: 1,
          },
        ],
      },
      options: {
        responsive: true,
        maintainAspectRatio: false,
      },
    });
  } catch (error) {
    console.error("Erro ao renderizar gráfico de distribuição de estoque:", error);
  }
}

renderStockDistributionChart();
async function updateMovementChart() {
  const canvas = document.getElementById("movementChart");
  if (!canvas) {
    console.error("Canvas 'movementChart' não encontrado no DOM.");
    return;
  }

  const ctx = canvas.getContext("2d");

  try {
    const response = await fetch("http://localhost:8080/estoque/movimentacoes");
    const movimentacoes = await response.json();

    const movimentacaoPorDataHora = movimentacoes.reduce((acc, mov) => {
      const dataHora = mov.data.replace("T", " ").slice(0, 16); // Inclui data e hora (YYYY-MM-DD HH:mm)
      if (!acc[dataHora]) acc[dataHora] = { Entrada: 0, Saída: 0 };
      acc[dataHora][mov.tipo] += mov.quantidade;
      return acc;
    }, {});
    
    const labels = Object.keys(movimentacaoPorDataHora).sort(); // Ordena por data e hora
    const entradas = labels.map(dateTime => movimentacaoPorDataHora[dateTime].Entrada || 0);
    const saidas = labels.map(dateTime => movimentacaoPorDataHora[dateTime].Saída || 0);
    
    // Verifica se o gráfico já existe e o destrói antes de criar um novo
    if (window.movementChart && typeof window.movementChart.destroy === "function") {
      window.movementChart.destroy();
    }

    // Cria um novo gráfico e atribui à variável global
    window.movementChart = new Chart(ctx, {
      type: "line",
      data: {
        labels: labels,
        datasets: [
          {
            label: "Entradas",
            data: entradas,
            borderColor: "rgba(75, 192, 192, 1)",
            backgroundColor: "rgba(75, 192, 192, 0.5)",
            fill: true,
          },
          {
            label: "Saídas",
            data: saidas,
            borderColor: "rgba(255, 99, 132, 1)",
            backgroundColor: "rgba(255, 99, 132, 0.5)",
            fill: true,
          },
        ],
      },
      options: {
        responsive: true,
        maintainAspectRatio: false,
        plugins: {
          legend: {
            position: "top",
          },
        },
        scales: {
          x: {
            title: {
              display: true,
              text: "Data e Hora",
            },
            ticks: {
              maxRotation: 45, // Inclina as labels para evitar sobreposição
              minRotation: 45,
            },
          },
          y: {
            title: {
              display: true,
              text: "Quantidade",
            },
            beginAtZero: true,
          },
        },
      },      
    });
  } catch (error) {
    console.error("Erro ao atualizar gráfico de movimentação:", error);
  }
}

// Garante que o DOM foi carregado antes de chamar a função
document.addEventListener("DOMContentLoaded", () => {
  updateMovementChart();
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
