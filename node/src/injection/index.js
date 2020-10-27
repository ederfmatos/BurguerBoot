class App {
  constructor() {
    this.socket = new WebSocket(`ws://localhost:9090/socket`);

    this.socket.onmessage = ({ data }) => {
      const { id, message } = JSON.parse(data);
      WAPI.sendMessage2(id, message);
    };
  }

  start() {
    this.waitNewMessages();
  }

  receiveMessage(message) {
    this.socket.send(JSON.stringify(message));
  }

  waitNewMessages() {
    WAPI.waitNewMessages(false, async messages => {
      for (const message of messages) {
        if (!this.isBlockedMessage(message)) {
          this.receiveMessage({
            message: message.content,
            id: message.chat.id._serialized,
            phoneNumber: message.chat.id.user,
            name: message.sender.name,
          });
        }
      }
    });
  }

  isBlockedMessage(message) {
    return ![
      'Raul Avezu',
      'Allan Henrique',
      'Brenda Lorençon',
      'Daniel Donatto',
      'Isabela',
      'Talison',
    ].includes(message.sender.name);
  }
}

const app = new App();
app.start();
