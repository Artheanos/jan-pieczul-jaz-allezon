/*  TODO    poprawienie lokatorow
            napisanie
*/

function deleteFromArray(array, obj) {
    let i = array.indexOf(obj);
    if (i !== -1)
        array.splice(array.indexOf(obj), 1);
}

document.onkeydown = function (e) {
    if (e.key === 'x')
        console.log(addedParameters, removedParameters);
    if (e.key === 'Enter')
        document.getElementsByClassName('dummy')[0].onclick();
    if (e.key === 'Delete') {
        let warningMessage = document.getElementsByClassName('repeated');
        if (warningMessage.length !== 0) {
            warningMessage[0].parentElement.remove();
            return
        }
        let elements = document.getElementsByClassName('parameter');
        console.log(elements.constructor.name);
        if (elements.length > 0)
            elements[elements.length - 1].children[1].onmousedown();
    }
};

let addedParameters = [];
let removedParameters = [];
let previousParameters = [];

for (let i of document.getElementsByClassName('parameter-value'))
    previousParameters.push(i.textContent);

function focusFirstInput() {
    let e = document.getElementsByClassName('unfinished')[0];
    if (e) e.focus()
}

function deleteTheMessage() {
    for (let i of document.getElementsByClassName('repeated'))
        i.parentElement.remove();
}

function giveListenerToButtons() {
    for (let parameter of document.getElementsByClassName('parameter')) {

        let parameterValue = parameter.children[0].textContent;

        parameter.children[1].onmousedown = function () {
            parameter.parentElement.remove();
            for (let i of document.getElementsByClassName('unfinished')) i.parentElement.remove();
            if (addedParameters.includes(parameterValue))
                deleteFromArray(addedParameters, parameterValue);
            else {
                removedParameters.push(parameterValue);
                deleteFromArray(previousParameters, parameterValue)
            }
        }
    }
}

function giveListenerToInputs() {

    for (let i of document.getElementsByClassName('unfinished')) {
        i.onchange = function () {
            deleteTheMessage();
            let newValue = this.value;

            if (addedParameters.includes(newValue) || previousParameters.includes(newValue))
                this.parentElement.innerHTML = '<td class="new-parameter repeated">' + 'This value already exists' + '</td>';
            else if (removedParameters.includes(newValue)) {
                deleteFromArray(removedParameters, newValue);
                this.parentElement.innerHTML = '<td class="parameter"><div class="parameter-value">' + newValue + '</div><button class="btn btn-danger">X</button></td>';
            } else {
                addedParameters.push(newValue);
                this.parentElement.innerHTML = '<td class="parameter new-parameter"><div class="parameter-value">' + newValue + '</div><button class="btn btn-danger">X</button></td>';
            }

            giveListenerToButtons();
            focusFirstInput();
        }
    }
}

giveListenerToButtons();

document.getElementsByClassName('dummy')[0].onclick = function () {
    deleteTheMessage();

    if (document.getElementsByClassName('unfinished').length === 0) {

        let tr = document.createElement('tr');
        let inputElement = document.createElement('input');
        inputElement.setAttribute('class', 'unfinished');
        inputElement.setAttribute('type', 'text');
        tr.appendChild(inputElement);

        document.getElementsByTagName('tbody')[0].insertBefore(tr, this);

        giveListenerToInputs();
        focusFirstInput();
    }
};

document.getElementById('formId:commandButton').onmousedown = function () {
    document.getElementsByName('formId:addedParameters')[0].value = addedParameters.join('|');
    document.getElementsByName('formId:removedParameters')[0].value = removedParameters.join('|');
};