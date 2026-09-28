const API_URL = "http://localhost:8081/api/products";

const productsContainer = document.getElementById("products");
const statusMessage = document.getElementById("status");

function createProductCard(product) {
    const card = document.createElement("article");
    card.className = "product-card";

    const name = document.createElement("h3");
    name.textContent = product.name;

    const price = document.createElement("p");
    price.className = "product-price";
    price.textContent = new Intl.NumberFormat("en-US", {
        style: "currency",
        currency: "USD"
    }).format(product.price);

    card.append(name, price);
    return card;
}

async function loadProducts() {
    try {
        const response = await fetch(API_URL);

        if (!response.ok) {
            throw new Error(`Request failed with status ${response.status}`);
        }

        const products = await response.json();
        productsContainer.replaceChildren(...products.map(createProductCard));
        statusMessage.textContent = products.length === 0 ? "No products found." : "";
    } catch (error) {
        statusMessage.textContent = "Unable to load products from the backend.";
        statusMessage.className = "error";
        console.error(error);
    }
}

loadProducts();
