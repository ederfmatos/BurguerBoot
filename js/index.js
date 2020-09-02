const $ = document.querySelector.bind(document)
const sendButton = $('#sendButton')
const inputText = $('#inputText')

function onSendButtonClick(){
    alert("Clicou no botão!")
}

function onEnterInputText(){
    alert("Apertou enter")
}

sendButton.addEventListener('click',onSendButtonClick)
inputText.addEventListener('keydown',(event)=>{

        if(event.keyCode == 13){
            event.preventDefault()
            onEnterInputText()
        }

})
