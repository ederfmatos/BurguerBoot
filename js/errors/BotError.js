class BotError {
  constructor(message) {
    this.message = message;
  }

  showError() {
    console.info(this);
  }
}
