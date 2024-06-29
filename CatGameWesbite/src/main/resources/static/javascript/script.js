
var id = null;

function catClicked() {
    var randomNumber = Math.floor(Math.random() * 2);
    console.log(randomNumber)
    var newCat = createNewCat()
    var catPosX = window.innerWidth / 2;
    var catPosY = window.innerHeight / 2;
    clearInterval(id);
    id = setInterval(frame, 0.1);
    function frame() {
        if (catPosX >= 1000) {
            console.log("Cat reach the limits.");
            clearInterval(id);
        }
        else {
            if (randomNumber == 0) {
                console.log("Cat is going to move.");
                catPosX++;
                catPosY++;
                newCat.style.left = catPosX + 'px';
                newCat.style.top = catPosY + 'px';
            }
            else {
                console.log("Cat is going to move.");
                catPosX++;
                catPosY++;
                newCat.style.right = catPosX + 'px';
                newCat.style.bottom = catPosY + 'px';
            }
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
    var container = document.getElementById("new-cat-image")
    container.appendChild(newCat);
    console.log("Created New Cat Image");
    return newCat;
}

function removeNewCat(newCat) {
    var newCat = document.getElementById("new-cat-image");
    newCat.remove();
}
