addColorInput();


function addColorInput() {
    const color_container = document.querySelector(".color-container");
    const index = color_container.children.length / 1;

    const input_block = document.createElement("div");

    const nameInput = document.createElement("input");
    nameInput.type = "text";
    nameInput.name = `variants[${index}].colorName`;
    nameInput.placeholder = "Color Name";
    nameInput.setAttribute("required", "");

    const codeInput = document.createElement("input");
    codeInput.type = "text";
    codeInput.name = `variants[${index}].colorCode`;
    codeInput.placeholder = "Color Code";
    codeInput.setAttribute("required", "");

    // =======================stock-container=======================

    const stock_area = document.createElement("div");
    stock_area.className = "stock-area";

    const addStockButton = document.createElement("button");
    addStockButton.type = "button";
    addStockButton.textContent = "Add Stock";
    addStockButton.onclick = function () {
        addStockInput(stock_container, index);
    };

    const stock_container = document.createElement("div");
    stock_container.className = "stock-container";

    addStockInput(stock_container, index);

    // =======================generation=======================

    //Layer1:color_container and input_block
    color_container.appendChild(input_block);
    input_block.appendChild(nameInput);
    input_block.appendChild(codeInput);
    input_block.appendChild(stock_area);

    //Layer2:stock_area
    stock_area.appendChild(addStockButton);
    stock_area.appendChild(stock_container);

}


function addStockInput(stock_container, index) {

    const sizeSelect = document.createElement("select");
    sizeSelect.name = `variants[${index}].sizes`;
    const sizes = ["S", "M", "L"];
    const inputs = [1, 2, 3];
    for (let i = 0; i < sizes.length; i++) {
        const option = document.createElement("option");
        option.value = inputs[i];
        option.text = sizes[i];
        sizeSelect.appendChild(option);
    }
    ;


    const stockInput = document.createElement("input");
    stockInput.type = "text";
    stockInput.name = `variants[${index}].stocks`;
    stockInput.placeholder = "Stock (positive integer)";
    stockInput.setAttribute("required", "");
    stockInput.setAttribute("pattern", "^[0-9]\\d*$");

    stock_container.appendChild(sizeSelect);
    stock_container.appendChild(stockInput);
}