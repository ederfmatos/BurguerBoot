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
  const message = inputText.value;

  if (message && message.trim()) {
    createMessage({ myMessage: true, message });
    inputText.value = "";
    bot.respond(message);
  }

  inputText.focus();
}

function createMessage({ myMessage, message }) {
  const messageItem = document.createElement("li");
  messageItem.classList.add(myMessage ? "myMessage" : "botMessage");
  messageItem.textContent = message;
  addNewMessage(messageItem);
}

function addNewMessage(message) {
  messageList.appendChild(message);
  scrollToBottom();
}

function scrollToBottom() {
  messageList.scrollTo(0, messageList.scrollHeight);
}
