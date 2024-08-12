const jwt = localStorage.getItem("jwt");
const payload = JSON.parse(atob(jwt.split('.')[1]));
document.querySelector(".main-username").textContent = payload.user.name;
document.querySelector(".main-orderNumber").textContent = sessionStorage.getItem("odrer-number");

function switchCategoryPage(category) {
    const targetUrl = `index.html?category=${category}`;
    window.location.href = targetUrl;
}

function switchToProfile(){
    window.location.href = "profile.html";
}