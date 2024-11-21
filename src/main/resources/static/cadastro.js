document.addEventListener("DOMContentLoaded", function () {
    const form = document.getElementById("cadastroForm");

    form.addEventListener("submit", async function (event) {
        event.preventDefault(); // Impede o envio padrão do formulário

        // Captura os dados do formulário
        const formData = {
            nome: document.getElementById("nome").value,
            username: document.getElementById("username").value,
            senha: document.getElementById("senha").value,
            cargo: document.querySelector('input[name="cargo"]:checked').value
        };

        try {
            // Envia os dados para o backend usando fetch
            const response = await fetch("/usuarios", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(formData)
            });

            // Verifica se a resposta foi bem-sucedida
            if (response.ok) {
                // Exibe o alert com a mensagem de sucesso
                alert("Usuário cadastrado com sucesso!");

                // Redireciona para a página inicial (index) após o alerta
                window.location.href = "/";  // Aqui você pode mudar a URL para a página inicial correta

                // Limpa os campos do formulário (não será necessário devido ao redirecionamento imediato)
                form.reset();
            } else {
                // Tenta pegar a resposta como JSON
                const errorData = await response.json();
                alert("Erro ao cadastrar usuário: " + errorData.message); // Exibe a mensagem de erro
            }
        } catch (error) {
            // Aqui o catch vai lidar com problemas de rede ou erros de requisição.
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