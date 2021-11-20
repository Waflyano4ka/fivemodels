let popup = document.getElementById('mypopup'),
    popupToggle = document.getElementById('myBtn');
let enable = false;

popupToggle.onclick = function () {
    if (!enable) {
        popup.className = "open";
        popupToggle.textContent = 'Скрыть';
        enable = true;
    } else {
        popup.className = "close";
        popupToggle.textContent = 'Добавить';
        enable = false;
    }
}