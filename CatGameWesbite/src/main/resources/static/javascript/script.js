
let id = null;
let leftLimit = 0;
let topLimit = 0;
let rightLimit = window.innerWidth;
let bottomLimit = window.innerHeight;
let score = 0;
let catPress = false;
let time = 30;
const canvas = document.querySelector('#confetti');
const jsConfetti = new JSConfetti();
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
        jsConfetti.addConfetti({
            emojis: ['🌈', '⚡️', '💥', '✨', '💫', '🌸'],
        }).then(() => jsConfetti.addConfetti())
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

