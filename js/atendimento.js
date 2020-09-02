class Attendance {
  constructor() {
    this.started = false;
    this.finished = false;
    this.customer = { name: "Eder Matos" };
    this.messages = [];
  }

  start() {
    this.createdAt = new Date().getTime();
    this.started = true;
    this.finished = false;
    this.messages = [];
  }

  addMessage(message) {
    this.messages.push(message);
  }

  isStarted() {
    return Boolean(this.started) && this.finished === false;
  }

  getMessages() {
    if (this.messages.length === 0) {
      return [];
    }

    return [...this.messages.slice(1, this.messages.length)];
  }

  getLastMessage() {
    if (this.messages.length < 3) {
      return null;
    }

    return { ...this.messages[this.messages.length - 1] };
  }

  finish() {
    this.finished = true;
    this.finishedAt = new Date().getTime();
  }
}
