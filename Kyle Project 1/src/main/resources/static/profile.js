if (localStorage.getItem("jwt") === null) {
    showAccountPage();
} else {
    showUserProfile();
}

function showAccountPage() {
    //clean all
    const allMainChildDivs = Array.from(document.querySelector(".main").children);
    allMainChildDivs.forEach(div => div.style.display = "none");
    //show account page
    document.querySelector(".main-account").style.display = "block";
}

function showUserProfile() {
    //clean all
    const allMainChildDivs = Array.from(document.querySelector(".main").children);
    allMainChildDivs.forEach(div => div.style.display = "none");
    //show profile page
    document.querySelector(".main-profile").style.display = "block";
    const jwt = localStorage.getItem("jwt");
    const payload = JSON.parse(atob(jwt.split('.')[1]));
    const expiry = payload.exp;
    if (Math.floor(Date.now() / 1000) <= expiry) {
        document.querySelector(".main-profile-username input").value = payload.user.name;
        document.querySelector(".main-profile-email input").value = payload.user.email;
    } else {
        console.log("please sigin in again");
        localStorage.removeItem("jwt");
        showAccountPage();
    }

}

document.querySelector(".main-account-switch").addEventListener("click", event => {
    const usernameDiv = document.querySelector(".main-account-username");
    const title = document.querySelector(".main-account-h1");
    if (title.textContent === "Sign in") {
        title.textContent = "Sign up"
        usernameDiv.style.display = "block";
        usernameDiv.children[1].setAttribute("required", "required");
    } else {
        title.textContent = "Sign in"
        usernameDiv.style.display = "none";
        usernameDiv.children[1].removeAttribute("required");
    }
});

document.querySelector(".main-account form").addEventListener("submit", event => {
    event.preventDefault();
    const title = document.querySelector(".main-account-h1");
    const formDataJson = {}
    //sign up
    if (title.textContent === "Sign up") {
        formDataJson.name = document.querySelector(".main-account-username input").value;
        formDataJson.email = document.querySelector(".main-account-email input").value;
        formDataJson.password = document.querySelector(".main-account-password input").value;
        fetch("/api/1.0/user/signup", {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(formDataJson)
        })
            .then(response => {
                if (!response.ok) {
                    switch (response.status) {
                        case 400:
                            alert("Sign up process went wrong.(400)");
                            break;
                        case 403:
                            alert("The account is already exits.(403)");
                            break;
                        default:
                            alert("Something went wrong.");
                    }
                }
                return response.json();
            })
            .then(data => {

                let jwt = data.data.access_token;
                // let user = data.data.user;
                localStorage.setItem("jwt", jwt);
                alert("Sign up success.");
                showUserProfile()
            })
            .catch(error => console.log("sign up failed"));
    }
    //sign in
    else {
        formDataJson.email = document.querySelector(".main-account-email input").value;
        formDataJson.password = document.querySelector(".main-account-password input").value;
        fetch("/api/1.0/user/signin", {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(formDataJson)
        })
            .then(response => {
                if (!response.ok) {
                    switch (response.status) {
                        case 400:
                            alert("Sign in process went wrong.(400)");
                            break;
                        case 403:
                            alert("The email or password is wrong.(403)");
                            break;
                        default:
                            alert("Something went wrong.");
                    }
                }
                return response.json();
            })
            .then(data => {
                let jwt = data.data.access_token;
                // let user = data.data.user;
                localStorage.setItem("jwt", jwt);
                alert("Sign in success.");
                showUserProfile()
            })
            .catch(error => console.log("sign in failed"));
    }
});

function switchCategoryPage(category) {
    const targetUrl = `index.html?category=${category}`;
    window.location.href = targetUrl;
}

function switchToProfile(){
    window.location.href = "profile.html";
}

