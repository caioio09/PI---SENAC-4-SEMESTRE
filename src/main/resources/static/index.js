document.addEventListener("DOMContentLoaded", function () {
    const loginForm = document.getElementById("loginForm");
    const errorMessage = document.getElementById("errorMessage");
  
    // Esconde a mensagem de erro inicialmente
    errorMessage.style.display = "none";
  
    loginForm.addEventListener("submit", async function (event) {
      event.preventDefault(); // Impede o envio padrão do formulário
  
      // Captura os dados do formulário
      const username = document.getElementById("username").value;
      const password = document.getElementById("password").value;
  
      try {
        // Envia a requisição para o backend
        const response = await fetch("/login", {
          method: "POST",
          headers: {
            "Content-Type": "application/x-www-form-urlencoded", // Enviando como form-urlencoded
          },
          body: new URLSearchParams({ username, senha: password }), // Formata os dados
        });
  
        if (response.ok) {
          // Redireciona para a página principal em caso de sucesso
          alert("Bem vindo "+ username)
          window.location.href = "home.html";
        } else {
          // Exibe a mensagem de erro caso as credenciais estejam incorretas
          errorMessage.style.display = "block";
        }
      } catch (error) {
        console.error("Erro ao tentar logar:", error);
        alert("Erro ao conectar ao servidor. Tente novamente mais tarde.");
      }
    });
  });
  