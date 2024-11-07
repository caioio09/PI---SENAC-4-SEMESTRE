document.getElementById('addProductForm').addEventListener('submit', function(e) {
    e.preventDefault();
    
    const productCode = document.getElementById('productCode').value;
    const productName = document.getElementById('productName').value;
    const productQuantity = document.getElementById('productQuantity').value;
    const productPrice = document.getElementById('productPrice').value;

    console.log('Produto adicionado:', {
        code: productCode,
        name: productName,
        quantity: productQuantity,
        price: productPrice
    });

    document.getElementById('successMessage').style.display = 'block';

    this.reset();

    setTimeout(() => {
        document.getElementById('successMessage').style.display = 'none';
    }, 3000);
});

document.querySelector('.search-bar').addEventListener('keyup', function(e) {
    if (e.key === 'Enter') {
        console.log('Buscar por:', this.value);
        // Aqui você implementaria a lógica de busca
        alert('Busca realizada por: ' + this.value);
        this.value = '';
    }
});