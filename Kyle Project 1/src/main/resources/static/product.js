const urlParams = new URLSearchParams(window.location.search);
const productId = urlParams.get("id");

//product
let variants;
let currentStockOfSize;

//checkout

showProduct(productId);
showCheckoutButton();


//==================================================product==================================================

//show the product information
function showProduct(productId) {
    //get data from back-end
    fetch("/api/1.0/products/details?id=" + productId)
        .then(response => response.json())
        .then(data => {
            const product = data.data;
            variants = product.variants;
            // set product information into front-end
            document.querySelector(".main-content-picture-img").src = product.main_image;
            document.querySelector(".main-content-info-title").innerHTML = product.title;
            document.querySelector(".main-content-info-id").innerHTML = "20180720214" + product.id;
            document.querySelector(".main-content-info-price").innerHTML = "TWD." + product.price;
            const colorSelector = document.querySelector(".main-content-info-variant-color-selector");
            product.colors.forEach(color => {
                const colorBlock = document.createElement("div");
                colorBlock.style.backgroundColor = color.code;
                colorBlock.classList.add("main-content-info-variant-color-selector-block-disable");
                colorBlock.addEventListener("click", function () {
                    setSizeByColor(colorBlock, variants.filter(variant => {
                        return rgbToHex(colorBlock.style.backgroundColor) === variant.color_code
                    }))
                });
                colorSelector.appendChild(colorBlock);
            });
            const sizeSelector = document.querySelector(".main-content-info-variant-size-selector");
            product.sizes.forEach(size => {
                const sizeBlock = document.createElement("div");
                sizeBlock.innerHTML = size;
                sizeBlock.classList.add("main-content-info-variant-size-selector-block-disable");
                sizeSelector.appendChild(sizeBlock);
            });
            document.querySelector(".main-content-info-note").innerHTML = product.note;
            document.querySelector(".main-content-info-texture").innerHTML = product.texture;
            document.querySelector(".main-content-info-description").innerHTML = product.description;
            document.querySelector(".main-content-info-place").innerHTML = "素材產地 / " + product.place + "<br>加工產地 / " + product.place;
            document.querySelector(".main-details-story").innerHTML = product.story;
            const imagesDiv = document.querySelector(".main-details-images");
            product.images.forEach(image => {
                const imageImg = document.createElement("img");
                imageImg.src = image;
                imageImg.classList.add(".main-details-images-img");
                imagesDiv.appendChild(imageImg);
            });
            // set default choose (the first color)
            const colorBlocks = Array.from(colorSelector.children);
            for (let i = 0; i < variants.length; i++) {
                if (variants[i].stock !== 0) {
                    const colorBlock = (colorBlocks.filter(colorBlock => rgbToHex(colorBlock.style.backgroundColor) === variants[i].color_code)[0]);
                    const fitVariants = variants.filter(v => v.color_code === variants[i].color_code);
                    setSizeByColor(colorBlock, fitVariants);
                    break;
                }
            }
        });
}

function setSizeByColor(colorBlock, variants) {
    // clear other color block
    cleanColor();
    cleanSize();
    // set the color's size
    colorBlock.classList.remove("main-content-info-variant-color-selector-block-disable");
    colorBlock.classList.add("main-content-info-variant-color-selector-block");
    const sizeSelector = document.querySelector(".main-content-info-variant-size-selector");
    const sizeBlocks = Array.from(sizeSelector.children);
    sizeBlocks.forEach(sizeBlock => {
        let variantsFitSize = variants.filter(variant => {
            return variant.size === sizeBlock.textContent && variant.stock !== 0
        });
        if (variantsFitSize.length) {
            sizeBlock.classList.remove("main-content-info-variant-size-selector-block-disable");
            sizeBlock.classList.add("main-content-info-variant-size-selector-block");
            (function (variantsFitSize) {
                sizeBlock.addEventListener("click", function () {
                    // clean
                    cleanSize();
                    // set
                    setStockNumberBySize(variantsFitSize[0]);
                    sizeBlock.setAttribute("id", "choose");
                });
            })(variantsFitSize);
        }
    });
    //set default choose (the first size which has stock)
    for (let i = 0; i < sizeBlocks.length; i++) {
        let variantsFitSize = variants.filter(variant => {
            return variant.size === sizeBlocks[i].textContent && variant.stock !== 0
        });
        if (variantsFitSize.length) {
            sizeBlocks[i].setAttribute("id", "choose");
            currentStockOfSize = variantsFitSize[0].stock;
            break;
        }
    }
}

function setStockNumberBySize(variant) {
    currentStockOfSize = variant.stock;
}

function cleanColor() {
    const colorBlocks = Array.from(document.querySelector(".main-content-info-variant-color-selector").children);
    colorBlocks.forEach(colorBlock => {
        colorBlock.classList.remove("main-content-info-variant-color-selector-block");
        colorBlock.classList.add("main-content-info-variant-color-selector-block-disable");
    });
    const sizeBlocks = Array.from(document.querySelector(".main-content-info-variant-size-selector").children);
    sizeBlocks.forEach(sizeBlock => {
        sizeBlock.classList.remove("main-content-info-variant-size-selector-block");
        sizeBlock.classList.add("main-content-info-variant-size-selector-block-disable");
        sizeBlock.removeAttribute("id");
    });
}

function cleanSize() {
    const sizeBlocks = Array.from(document.querySelector(".main-content-info-variant-size-selector").children);
    sizeBlocks.forEach(sizeBlock => {
        sizeBlock.removeAttribute("id");
    });
    document.querySelector(".main-content-info-variant-stock-selector-value").textContent = 1;
}

function rgbToHex(rgb) {
    const result = rgb.match(/^rgb\((\d+),\s*(\d+),\s*(\d+)\)$/);
    if (!result) {
        throw new Error('Invalid RGB color format');
    }

    const r = parseInt(result[1]).toString(16).padStart(2, '0');
    const g = parseInt(result[2]).toString(16).padStart(2, '0');
    const b = parseInt(result[3]).toString(16).padStart(2, '0');

    return `#${r}${g}${b}`;
}

function stockAdd() {
    const stockValueDiv = document.querySelector(".main-content-info-variant-stock-selector-value");
    if (Number(stockValueDiv.textContent) + 1 <= currentStockOfSize)
        stockValueDiv.textContent = Number(stockValueDiv.textContent) + 1;
}

function stockMinus() {
    const stockValueDiv = document.querySelector(".main-content-info-variant-stock-selector-value");
    if (Number(stockValueDiv.textContent) - 1 > 0)
        stockValueDiv.textContent = Number(stockValueDiv.textContent) - 1;
}

function showCheckoutButton() {
    if (localStorage.getItem("jwt") !== null) {
        const jwt = localStorage.getItem("jwt");
        const payload = JSON.parse(atob(jwt.split('.')[1]));
        if (Math.floor(Date.now() / 1000) <= payload.exp) {
            document.querySelector("#checkout-button-withoutSignIn").style.display = "none";
            document.querySelector("#checkout-button-withSignIn").style.display = "block";
        } else {
            document.querySelector("#checkout-button-withoutSignIn").style.display = "block";
            document.querySelector("#checkout-button-withSignIn").style.display = "none";
        }
    } else {
        document.querySelector("#checkout-button-withoutSignIn").style.display = "block";
        document.querySelector("#checkout-button-withSignIn").style.display = "none";
    }

}

function checkoutWithSignin() {
    alert("請先登入");
    window.location.href = "profile.html";
}

function switchCategoryPage(category) {
    const targetUrl = `index.html?category=${category}`;
    window.location.href = targetUrl;
}

function switchToProfile(){
    window.location.href = "profile.html";
}


//==================================================checkout==================================================


TPDirect.setupSDK(152571, 'app_GbjnFH6Jzr5GEMPhTEGufzRpeCraCMWSfIwYZONhTsQzZ3C98oj1D3e5VhjS', 'sandbox')
TPDirect.card.setup({
    // Not display ccv field
    fields: {
        number: {
            // css selector
            element: '#card-number',
            placeholder: '**** **** **** ****'
        },
        expirationDate: {
            // DOM object
            element: document.getElementById('card-expiration-date'),
            placeholder: 'MM / YY'
        },
        ccv: {
            element: '#card-ccv',
            placeholder: 'ccv'
        }
    },
    styles: {
        // Style all elements
        'input': {
            'color': 'gray'
        },
        // Styling ccv field
        'input.ccv': {
            // 'font-size': '16px'
        },
        // Styling expiration-date field
        'input.expiration-date': {
            // 'font-size': '16px'
        },
        // Styling card-number field
        'input.card-number': {
            // 'font-size': '16px'
        },
        // style focus state
        ':focus': {
            // 'color': 'black'
        },
        // style valid state
        '.valid': {
            'color': 'green'
        },
        // style invalid state
        '.invalid': {
            'color': 'red'
        },
        // Media queries
        // Note that these apply to the iframe, not the root window.
        '@media screen and (max-width: 400px)': {
            'input': {
                'color': 'orange'
            }
        }
    },
    // 此設定會顯示卡號輸入正確後，會顯示前六後四碼信用卡卡號
    isMaskCreditCardNumber: true,
    maskCreditCardNumberRange: {
        beginIndex: 6,
        endIndex: 11
    }
});

TPDirect.card.onUpdate(function (update) {
    // const submitButton = document.querySelector(".submitButton");
    // // update.canGetPrime === true
    // // --> you can call TPDirect.card.getPrime()
    // if (update.canGetPrime) {
    //     // Enable submit Button to get prime.
    //     submitButton.removeAttribute('disabled')
    // } else {
    //     // Disable submit Button to get prime.
    //     submitButton.setAttribute('disabled', true)
    // }

    // cardTypes = ['mastercard', 'visa', 'jcb', 'amex', 'unionpay','unknown']
    if (update.cardType === 'visa') {
        // Handle card type visa.
    }

    // number 欄位是錯誤的
    if (update.status.number === 2) {
        // setNumberFormGroupToError()
    } else if (update.status.number === 0) {
        // setNumberFormGroupToSuccess()
    } else {
        // setNumberFormGroupToNormal()
    }

    if (update.status.expiry === 2) {
        // setNumberFormGroupToError()
    } else if (update.status.expiry === 0) {
        // setNumberFormGroupToSuccess()
    } else {
        // setNumberFormGroupToNormal()
    }

    if (update.status.ccv === 2) {
        // setNumberFormGroupToError()
    } else if (update.status.ccv === 0) {
        // setNumberFormGroupToSuccess()
    } else {
        // setNumberFormGroupToNormal()
    }
})

function onSubmit(event) {
    event.preventDefault();

    // 取得 TapPay Fields 的 status
    const tappayStatus = TPDirect.card.getTappayFieldsStatus()

    // 確認是否可以 getPrime
    if (tappayStatus.canGetPrime === false) {
        alert('can not get prime')
        return
    }

    // Get prime
    TPDirect.card.getPrime((result) => {
        if (result.status !== 0) {
            alert('get prime error ' + result.msg)
            return
        }

        let itemPrice = Number(document.querySelector(".main-content-info-price").textContent.split('.')[1]);
        let itemNumber = Number(document.querySelector(".main-content-info-variant-stock-selector-value").textContent);

        let color_code;
        let color_name;
        const colorBlocks = Array.from(document.querySelector(".main-content-info-variant-color-selector").children);
        colorBlocks.forEach(colorBlock => {
            if (colorBlock.className === "main-content-info-variant-color-selector-block") {
                variants.filter(variant => {
                    return rgbToHex(colorBlock.style.backgroundColor) === variant.color_code
                })
            }
        });

        let size;
        const sizeBlocks = Array.from(document.querySelector(".main-content-info-variant-size-selector").children);
        sizeBlocks.forEach(sizeBlock => {
            if (sizeBlock.id !== null)
                size = sizeBlock.textContent;
        });


        let requestData = {
            "prime": result.card.prime,
            "order": {
                "shipping": "delivery",
                "payment": "credit_card",
                "subtotal": itemPrice * itemNumber,
                "freight": 14,
                "total": itemPrice * itemNumber + 14,
                "recipient": {
                    "name": "Luke",
                    "phone": "0987654321",
                    "email": "luke@gmail.com",
                    "address": "市政府站",
                    "time": "morning"
                },
                "list": [
                    {
                        "id": document.querySelector(".main-content-info-id").textContent,
                        "name": document.querySelector(".main-content-info-title").textContent,
                        "price": itemPrice,
                        "color": {
                            "code": variants.color_code,
                            "name": variants.color_name
                        },
                        "size": size,
                        "qty": itemNumber
                    }
                ]
            }
        };
        let jwt = localStorage.getItem("jwt");
        fetch("/api/1.0/order/checkout", {
            method: "POST",
            headers: {"Content-Type": "application/json", "Authorization": "Bearer " + jwt},
            body: JSON.stringify(requestData)
        })
            .then(response => response.json())
            .then(data => {
                sessionStorage.setItem("odrer-number", data.data.number);
                window.location.href = "thankyou.html";
            })
            .catch(error => console.log(data));
    })
}

