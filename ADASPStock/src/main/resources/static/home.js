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
  