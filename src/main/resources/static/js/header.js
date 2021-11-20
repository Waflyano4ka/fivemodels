const buttons = document.getElementsByClassName('nav-link');
let currentLocation = window.location.pathname;

for (var i = 0; i < 5; i++) {
    buttons[i].className = 'nav-link';
    if (currentLocation.includes(buttons[i].pathname)){
        buttons[i].classList.add('active')
    }
}
