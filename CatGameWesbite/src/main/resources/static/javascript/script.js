
var id = null;

function catMoving() {
    var cat = document.getElementById("cat");
    var catPos = cat.getBoundingClientRect();
    console.log("Position cat: " + catPos.x + catPos.y);
    clearInterval(id);
    id = setInterval(frame, 10);
    function frame() {
        if (catPos == 350) {
            clearInterval(id);
        }
        else {
            catPos++;
            cat.style.top = catPos + 'px';
            cat.style.left = catPos + 'px';
        }
    }
}
