class Attendance {
  constructor() {
    this.started = false;
    this.finished = false;
    this.customer = { name: "Eder Matos" };
    this.messages = [];
    this.lastChildAction = 0;
  }

  start() {
    this.createdAt = new Date().getTime();
    this.started = true;
    this.finished = false;
    this.messages = [];
    this.lastMessage = null;
  }

  addMessage(message) {
    this.messages.push(message);
  }

  isStarted() {
    return Boolean(this.started) && this.finished === false;
  }

  getMessages({ less = 0 }) {
    if (this.messages.length === 0) {
      return [];
    }

    return [...this.messages.slice(1, this.messages.length - less)];
  }

  getLastMessage() {
    return this.lastMessage;
  }

  setLastMessage(lastMessage) {
    this.lastMessage = lastMessage;
  }

  incrementLastChildNumber() {
    this.lastChildAction++;
  }

  finish() {
    this.finished = true;
    this.finishedAt = new Date().getTime();
  }
}
