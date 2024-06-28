
var id = null;

function catMoving() {
    var cat = document.getElementById("cat-image");
    var catPos = 0;
    clearInterval(id);
    id = setInterval(frame, 10);
    function frame() {
        if (catPos >= 700) {
            clearInterval(id);
        }
        else {
            catPos++;
            cat.style.left = catPos + 'px';
            cat.style.top = catPos + 'px';
            console.log("CAT LEFT " + cat.style.left)
        }
    }
}

