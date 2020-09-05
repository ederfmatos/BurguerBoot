class Drinks extends RequestOption {
  getValue() {
    return "2";
  }

  getName() {
    return "Bebidas";
  }

  getFirstItemFinishOption() {
    return {
      name: "Pedir outra bebida",
      value: "1",
      onSelect(option, message, attendance) {
        attendance.setLastMessage(this.bot.messages[0]);
        attendance.lastChildAction = -1;
        return this.bot.getResponseFromMessage({ message: this.getValue() });
      },
    };
  }

  getOptions() {
    return [
      {
        name: "Coca cola 600ml",
        value: "1",
      },
      {
        name: "Fanta Guaraná",
        value: "2",
      },
    ];
  }

  chooseOptionsFinish(option, message) {
    this.validateQuantity(message);
    attendance.changeLastProduct({ quantity: parseInt(message, 10) });
    return super.chooseOptionsFinish(...arguments);
  }

  getOnSelectChild() {
    return [
      this.getQuantity.bind(this),
      this.chooseOptionsFinish.bind(this),
      this.handleFinishOptions.bind(this),
    ];
  }
}
