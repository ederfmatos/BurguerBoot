class Drinks extends RequestOption {
  getValue() {
    return "2";
  }

  getName() {
    return "02 - Bebidas";
  }

  getFirstItemFinishOption() {
    return {
      text: "01 - Pedir outra bebida",
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
        text: "01 - Coca cola 600ml",
        value: "1",
      },
      {
        text: "02 - Fanta Guaraná",
        value: "2",
      },
    ];
  }

  getOnSelectChild() {
    return [
      this.getQuantity.bind(this),
      this.chooseOptionsFinish.bind(this),
      this.handleFinishOptions.bind(this),
    ];
  }
}
