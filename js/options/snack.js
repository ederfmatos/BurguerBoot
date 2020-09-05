function isNumber(n) {
  return /^-?[\d.]+(?:e-?\d+)?$/.test(n);
}

class Snack extends RequestOption {
  getValue() {
    return "1";
  }

  getName() {
    return "01 - Lanches";
  }

  getFirstItemFinishOption() {
    return {
      text: "01 - Pedir outro lanche",
      value: "1",
      onSelect(option, message, attendance) {
        attendance.setLastMessage(this.bot.messages[0]);
        attendance.lastChildAction = -1;
        return this.bot.getResponseFromMessage({ message });
      },
    };
  }

  getObservation(option, message) {
    if (!isNumber(message)) {
      throw new InvalidOptionError("Desculpe, não entendi, qual a quantidade?");
    }

    if (message <= 0) {
      throw new InvalidOptionError(
        "Desculpe, mas você tem que escolher pelo menos 1!"
      );
    }

    return `Ok, você deseja adicionar alguma observação para o lanche?
    
    01 - Sim
    02 - Não`;
  }

  handleObservation(option, message, attendance) {
    if (!["1", "2"].includes(message)) {
      throw new InvalidOptionError();
    }

    if ("1" === message) {
      return `Ok, qual observação você deseja inserir?`;
    }

    attendance.incrementLastChildNumber();

    return this.chooseOptionsFinish(...arguments);
  }

  getOptions() {
    return [
      {
        text: "01 - Sanduiche de presunto",
        value: "1",
      },
      {
        text: "02 - Pão com ovo",
        value: "2",
      },
    ];
  }

  getOnSelectChild() {
    return [
      this.getQuantity.bind(this),
      this.getObservation.bind(this),
      this.handleObservation.bind(this),
      this.chooseOptionsFinish.bind(this),
      this.handleFinishOptions.bind(this),
    ];
  }
}
