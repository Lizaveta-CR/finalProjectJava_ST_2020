if (window.history.replaceState) {
    window.history.replaceState(null, null, window.location.href);
}
var myVar;

function load() {
    myVar = setTimeout(showPage, 3000);
}

function showPage() {
    document.getElementById("loader").style.display = "none";
    document.getElementById("myDiv").style.display = "block";
}