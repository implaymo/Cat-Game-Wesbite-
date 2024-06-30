
var id = null;
var leftLimit = 0;
var topLimit = 0;
var rightLimit = window.innerWidth;
var bottomLimit = window.innerHeight;

function catClicked() {   
    var randomNumber = Math.floor(Math.random() * 4);
    var newCat = createNewCat()
    var catPosX = window.innerWidth / 2;
    var catPosY = window.innerHeight / 2;
    clearInterval(id);
    id = setInterval(frame, 0.1);
    function frame() {
        if (catPosX >= rightLimit || catPosY >= bottomLimit || catPosX <= leftLimit || catPosY <= topLimit) {
            console.log("Cat reach the limits.");
            console.log("CAT ID " + newCat.id);
            removeNewCat(newCat.id);
            clearInterval(id);
        }
        else {
            if (randomNumber == 0) {
                console.log("Cat is going to move.");
                catPosX++;
                catPosY++;
                newCat.style.left = catPosX + 'px';
            }
            else if (randomNumber == 1) {
                console.log("Cat is going to move.");
                catPosX++;
                catPosY++;
                newCat.style.right = catPosX + 'px';
            }
            else if (randomNumber == 2) {
                console.log("Cat is going to move.");
                catPosX++;
                catPosY++;
                newCat.style.bottom = catPosY + 'px';
            }
            else if (randomNumber == 3) {
                console.log("Cat is going to move.");
                catPosX++;
                catPosY++;
                newCat.style.top = catPosY + 'px';
            }
        }
    }
}

catId = 0;

function createNewCat() {
    var newCat = document.createElement("img");
    newCat.src = "cat.png";
    newCat.id = "new-cat-image " + catId;
    newCat.style.position = "absolute";
    newCat.width = 50;
    newCat.height = 50;
    var container = document.getElementById("new-cat-image")
    container.appendChild(newCat);
    console.log("Created New Cat Image");
    catId++;
    return newCat;
}

function removeNewCat(newCatId) {
    var catToRemove = document.getElementById(newCatId);
    catToRemove.remove();
}

function moveCat() {
    catPosX++;
    catPosY++;
}