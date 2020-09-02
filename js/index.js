const $ = document.querySelector.bind(document);
const sendButton = $("#sendButton");
const inputText = $("#inputText");
const messageList = $(".messages");

function onSendButtonClick() {
  sendMessage();
}

function onEnterInputText() {
  sendMessage();
}

sendButton.addEventListener("click", onSendButtonClick);
inputText.addEventListener("keydown", (event) => {
  if (event.keyCode == 13) {
    event.preventDefault();
    onEnterInputText();
  }
});

function sendMessage() {
  const text = inputText.value;

  if (text) {
    createMessage({ myMessage: true, text });
    inputText.value = "";
  }

  inputText.focus();
}

function createMessage({ myMessage, text }) {
  const messageItem = document.createElement("li");
  messageItem.classList.add(myMessage ? "myMessage" : "botMessage");
  messageItem.textContent = text;
  addNewMessage(messageItem);
}

function addNewMessage(message) {
  messageList.appendChild(message);
  scrollToBottom();
}

function scrollToBottom() {
  messageList.scrollTo(0, messageList.scrollHeight);
}
