
let id = null;
let leftLimit = 0;
let topLimit = 0;
let rightLimit = window.innerWidth;
let bottomLimit = window.innerHeight;
let score = 0;
let catPress = false;
let time = 30;
fetchHighscore();


const myTimer = setInterval(countDown, 1000);


function countClicks(){
    score ++;
    liveScore();
    console.log("TIMES CLICKED: " + score);
}

function liveScore() {
    document.getElementById("live-score").innerText = "Score: " + score;
}


function timerErrorMessage() {
        var errorMessageDiv = document.getElementById('error-message');
        if (errorMessageDiv) {
            setTimeout(function() {
                errorMessageDiv.style.display = 'none';
            }, 5000);
        } 
        else {
         console.log('Error message element not found.');
        }
    }    

    function stopTimer() {
        clearInterval(myTimer);
    }
    


function countDown(){
        if (score >= 1){
            time--;
            console.log("TIMER: " + time);
            document.getElementById("timer").innerHTML = time;  
        }
        if (time < 1) {
            resetGame();
        }
} 

function resetGame() {
    checkIfHighscore();
    time = 30;
    score = 0;
    document.getElementById("timer").innerText = 30;
    document.getElementById("live-score").innerText = "Score: 0";
}

function checkIfHighscore() {
    if (score > highscore) {
        console.log("New Highscore achieved: " + score);
        document.getElementById("highscore").innerText = "Highscore: " + score;
        sendNewHighscore(score);
    }
}

// Get highscore from users Database table
function fetchHighscore() {
    $.ajax({
        type: "GET",
        url: "/getuserhighscore",
        success: function(response) {
            console.log("Got user highscore from database. Highscore: ", response.highscore);
            highscore = response.highscore;
            document.getElementById("highscore").innerText = "Highscore: " + response.highscore;
        },
        error: function(error) {
            console.error("Error fetching highscore.", error);
        }
    });
}


// Send user score to server side
function sendNewHighscore(score) {
    $.ajax({
        type: "POST",
        url: "/updatehighscore",
        data: JSON.stringify({score: score}),
        contentType: 'application/json',
        dataType: 'json',
        success: function(response) {
            console.log("Success:", response.highscore);
        },
        error: function(error) {
            console.log("UPDATING HIGHSCORE TO DATABASE, BUT IM GETTING AN ERROR.")
            console.error("Error:", error);
        }
    });
}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
function ifGetHighscore() {
    // NOT BEING USED AT THE MOMENT. Needs new implementation. GOAL is to create an explosion of small cats in random directions if user gets a new ifHighscore
        catPress = true;
        score++;
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
        console.log("TIMES CLICKED: " + score)
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
    ////////////////////////////////////////////////////////////////////////////////////////////////////////7

