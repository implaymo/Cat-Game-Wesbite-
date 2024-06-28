
var id = null;

function catClicked() {
    var newCat = createNewCat()
    var catPos = 0;
    clearInterval(id);
    id = setInterval(frame, 10);
    function frame() {
        if (catPos >= 700) {
            console.log("Cat reach the limits.");
            clearInterval(id);
        }
        else {
            console.log("Cat is going to move.");
            catPos++;
            newCat.style.left = catPos + 'px';
            newCat.style.top = catPos + 'px';
        }
    }
}

function createNewCat() {
    var newCat = document.createElement("img");
    newCat.src = "cat.png";
    newCat.id = "new-cat-image";
    newCat.style.position = "absolute";
    newCat.width = 50;
    newCat.height = 50;
    newCat.style.left = "100px";
    newCat.style.top = "100px";
    var container = document.getElementById("new-cat-image")
    container.appendChild(newCat);
    console.log("Created New Cat Image");
    return newCat;
}

function removeNewCat(newCat) {
    var newCat = document.getElementById("new-cat-image");
    newCat.remove();
}
