class RequestOption {
  constructor(bot) {
    this.bot = bot;
  }

  getValue() {
    throw new Error();
  }

  getName() {
    throw new Error();
  }

  getOptions() {
    throw new Error();
  }

  getFirstItemFinishOption() {
    throw new Error();
  }

  getOnSelectChild() {
    throw new Error();
  }

  getFinishOptions() {
    return [
      this.getFirstItemFinishOption(),
      {
        text: "02 - Fazer outro pedido",
        value: "2",
        onSelect(option, message, attendance) {
          attendance.setLastMessage(null);
          attendance.lastChildAction = -1;
          return this.bot.getResponseFromMessage({ message: "1" });
        },
      },
      {
        text: "03 - Finalizar atentimento",
        value: "3",
        onSelect(option, message, attendance) {
          console.log("03 - Finalizar atentimento");
        },
      },
    ];
  }

  handleFinishOptions(option, message, attendance) {
    const finishOption = this.getFinishOptions().find(
      ({ value }) => value === message
    );

    if (finishOption) {
      return finishOption.onSelect.call(this, option, message, attendance);
    }

    throw new InvalidOptionError();
  }

  chooseOptionsFinish() {
    return `Ok, o que mais você deseja?
        
          ${this.bot.formatOptions(this.getFinishOptions())}
          `;
  }

  getQuantity(option) {
    return `Ok, você selecionou "${
      option.text.split(" - ")[1]
    }", qual a quantidade?"`;
  }

  getRequestObject() {
    return {
      text: this.getName(),
      value: this.getValue(),
      options: this.getOptions(),
      onSelectChild: this.getOnSelectChild(),
    };
  }
}
