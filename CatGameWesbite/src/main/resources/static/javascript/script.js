
var id = null;

function catClicked() {
    var cat = document.getElementById("cat-image");
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
            createNewCat();
            catPos++;
            cat.style.left = catPos + 'px';
            cat.style.top = catPos + 'px';
        }
    }
}

function createNewCat() {
    var newCat = document.createElement("img");
    newCat.src = "cat.png"
    newCat.width = 100;
    newCat.height = 100;
    newCat.style.left = "100px";
    newCat.style.top = "100px";
    var container = document.getElementById("new-cat-image")
    container.appendChild(newCat);
    console.log("Created New Cat Image");
}

