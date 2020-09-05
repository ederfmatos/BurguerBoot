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
        name: "Fazer outro pedido",
        value: "2",
        onSelect(option, message, attendance) {
          attendance.setLastMessage(null);
          attendance.lastChildAction = -1;
          return this.bot.getResponseFromMessage({ message: "1" });
        },
      },
      {
        name: "Finalizar atentimento",
        value: "3",
        onSelect(option, message, attendance) {
          if (attendance.hasProducts()) {
            return `${attendance.getAttendanceInformation()}
            ${this.bot.finishAttendance()}`;
          }

          return this.bot.finishAttendance();
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

  validateQuantity(quantity) {
    if (!isNumber(quantity)) {
      throw new InvalidOptionError("Desculpe, não entendi, qual a quantidade?");
    }

    if (quantity <= 0) {
      throw new InvalidOptionError(
        "Desculpe, mas você tem que escolher pelo menos 1!"
      );
    }
  }

  chooseOptionsFinish() {
    return `Ok, o que mais você deseja?
        
          ${this.bot.formatOptions(this.getFinishOptions())}
          `;
  }

  getQuantity({ name, price }, message, attendance) {
    attendance.addNewProduct({ name, price });

    return `Ok, você selecionou "${name}", qual a quantidade?"`;
  }

  getRequestObject() {
    return {
      name: this.getName(),
      value: this.getValue(),
      options: this.getOptions(),
      onSelectChild: this.getOnSelectChild(),
    };
  }
}
