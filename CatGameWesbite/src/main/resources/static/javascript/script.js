
var id = null;

function catClicked() {
    var cat = document.getElementById("cat-image");
    var catPos = 0;
    clearInterval(id);
    id = setInterval(frame, 10);
    newCat = createNewCat();
    function frame() {
        if (catPos >= 700) {
            console.log("Cat reach the limits.");
            removeNewCat(newCat);
            clearInterval(id);
        }
        else {
            console.log("Cat is going to move.");
            catPos++;
            cat.style.left = catPos + 'px';
            cat.style.top = catPos + 'px';
        }
    }
}

function createNewCat() {
    var newCat = document.createElement("img");
    newCat.src = "cat.png"
    newCat.width = 50;
    newCat.height = 50;
    newCat.style.left = "100px";
    newCat.style.top = "100px";
    var container = document.getElementById("new-cat-image")
    container.appendChild(newCat);
    console.log("Created New Cat Image");
}

function removeNewCat(newCat) {
    var newCat = document.getElementById("new-cat-image");
    newCat.remove();
}
