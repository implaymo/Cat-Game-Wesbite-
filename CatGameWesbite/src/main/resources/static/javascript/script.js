
let id = null;
let leftLimit = 0;
let topLimit = 0;
let rightLimit = window.innerWidth;
let bottomLimit = window.innerHeight;
let timesClick = 1;
let catPress = false;

const myTimer = setInterval(countDown, 1000);


function countClicks(){
    liveScore();

    if (time > 0){
        timesClick ++;
        console.log("TIMES CLICKED: " + timesClick);
    }
}

function highscore() {
// NOT BEING USED AT THE MOMENT. Needs new implementation. GOAL is to create an explosion of small cats in random directions if user gets a new highscore

    catPress = true;
    timesClick++;
    let randomNumber = Math.floor(Math.random() * 4);
    let newCat = createNewCat()
    let catPosX = window.innerWidth / 2;
    let catPosY = window.innerHeight / 2;
    clearInterval(id);
    id = setInterval(frame, 10);
    function frame() {
        if (catPosX >= rightLimit || catPosY >= bottomLimit || catPosX <= leftLimit || catPosY <= topLimit) {
            console.log("Cat reach the limits.");
            console.log("CAT ID " + newCat.id);
            removeNewCat(newCat.id);
            clearInterval(id);
        }
        else {
            console.log("Cat is going to move.");
            if (randomNumber == 0) {
                catPosX++;
                catPosY++;
                newCat.style.left = catPosX + 'px';
            }
            else if (randomNumber == 1) {
                catPosX++;
                catPosY++;
                newCat.style.right = catPosX + 'px';
            }
            else if (randomNumber == 2) {
                catPosX++;
                catPosY++;
                newCat.style.bottom = catPosY + 'px';
            }
            else if (randomNumber == 3) {
                catPosX++;
                catPosY++;
                newCat.style.top = catPosY + 'px';
            }
        }
    }
    console.log("TIMES CLICKED: " + timesClick)
}

catId = 0;
function createNewCat() {
    // NOT BEING USED AT THE MOMENT. 
    let newCat = document.createElement("img");
    newCat.src = "cat.png";
    newCat.id = "new-cat-image " + catId;
    newCat.style.position = "absolute";
    newCat.width = 50;
    newCat.height = 50;
    let container = document.getElementById("new-cat-image")
    container.appendChild(newCat);
    console.log("Created New Cat Image");
    catId++;
    return newCat;
}

function removeNewCat(newCatId) {
    // NOT BEING USED AT THE MOMENT. 
    let catToRemove = document.getElementById(newCatId);
    catToRemove.remove();
}

let time = 30;
function countDown(){
        if (timesClick >= 2){
            time--;
            console.log("TIMER: " + time);
            document.getElementById("timer").innerHTML = time;  
        }
        if (time === 0) {
            stopTimer();
        }
} 

function stopTimer() {
    clearInterval(myTimer);
}

function resetGame() {
    time = 30;
    timesClick = 0;
    document.getElementById("timer").innerText = 30;
    countDown();
}

function liveScore() {
    document.getElementById("live-score").innerText = "Score: " + timesClick;
}