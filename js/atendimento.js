class Attendance {
  constructor() {
    this.started = false;
    this.customer = { name: "Eder Matos" };
    this.messages = [];
  }

  start() {
    this.createdAt = new Date().getTime();
    this.started = true;
  }

  addMessage(message) {
    this.messages.push(message);
  }

  isStarted() {
    return Boolean(this.started);
  }
}
