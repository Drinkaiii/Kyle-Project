const urlParams = new URLSearchParams(window.location.search);
const category = urlParams.get("category");

if (category)
    showProduct(category);
else
    showProduct("all");
showCampaigns();

//show campaigns
function showCampaigns() {
    fetch("/api/1.0/marketing/campaigns")
        .then(response => response.json())
        .then(data => {
            // show product data
            let text = data.data[0].story;
            let lastIndex = text.lastIndexOf('\n');
            if (lastIndex === -1)
                lastIndex = text.lastIndexOf('\r\n');
            document.querySelector(".header-message-main p").innerHTML = text.substring(0, lastIndex)
            document.querySelector(".header-message-subtitle p").innerHTML = text.substring(lastIndex + 1);
            document.querySelector(".header img").src = data.data[0].picture;
            document.querySelector(".header").addEventListener("click", function () {
                switchToProductById(data.data[0].product_id);
            });
        });
}


//show all products
function showProduct(category) {
    fetch("/api/1.0/products/" + category)
        .then(response => response.json())
        .then(data => {
            const mainContent = document.querySelector(".main");
            // show product data
            data.data.forEach(product => {
                const productDiv = document.createElement("div");
                productDiv.classList.add("main-product");
                productDiv.addEventListener("click", function () {
                    switchToProductById(product.id)
                });

                const productImage = document.createElement("img");
                productImage.classList.add("main-product-img");
                productImage.src = product.main_image;
                productImage.alt = "product picture";

                const productColorDiv = document.createElement("div");
                productColorDiv.classList.add("main-product-color");
                product.colors.forEach(color => {
                    const colorBlock = document.createElement("div");
                    colorBlock.style.backgroundColor = color.code;
                    colorBlock.classList.add("main-product-color-block");
                    productColorDiv.appendChild(colorBlock);
                });

                const productName = document.createElement("p");
                productName.innerHTML = product.title;

                const priductPrice = document.createElement("p")
                priductPrice.innerHTML = "TWD." + product.price;

                productDiv.appendChild(productImage);
                productDiv.appendChild(productColorDiv);
                productDiv.appendChild(productName);
                productDiv.appendChild(priductPrice);

                mainContent.appendChild(productDiv);
            });
        })
        .catch(error => {
            console.error('Error fetching products:', error);
        });
}

function switchCategoryPage(category) {
    const targetUrl = `index.html?category=${category}`;
    window.location.href = targetUrl;
}

function switchToProductById(id) {
    const targetUrl = `product.html?id=${id}`;
    window.location.href = targetUrl;
}

function switchToProfile(){
    window.location.href = "profile.html";
}
