let a = document.getElementById('edit'),
    button = document.getElementById('editBtn');
    inputs = document.getElementsByClassName('form-control');
    select = document.getElementsByClassName('form-select');
let enable = false;

button.hidden = true;

a.onclick = function () {
    if (!enable) {
        a.remove();
        enable = true;
        button.hidden = false;

        for (let i = 0; i < inputs.length; i++) {
            inputs[i].readOnly = false;
        }
        if (select.length > 0)
            select[0].disabled = false;
    }
}