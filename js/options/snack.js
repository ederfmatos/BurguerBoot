function isNumber(n) {
  return /^-?[\d.]+(?:e-?\d+)?$/.test(n);
}

class Snack extends RequestOption {
  getValue() {
    return "1";
  }

  getName() {
    return "Lanches";
  }

  getFirstItemFinishOption() {
    return {
      name: "Pedir outro lanche",
      value: "1",
      onSelect(option, message, attendance) {
        attendance.setLastMessage(this.bot.messages[0]);
        attendance.lastChildAction = -1;
        return this.bot.getResponseFromMessage({ message });
      },
    };
  }

  getObservation(option, message) {
    this.validateQuantity(message);

    attendance.changeLastProduct({ quantity: parseInt(message, 10) });

    return `Ok, você deseja adicionar alguma observação para o lanche?
    
    01 - Sim
    02 - Não`;
  }

  handleObservation(option, message, attendance) {
    if (!["1", "2"].includes(message)) {
      throw new InvalidOptionError();
    }

    if ("1" === message) {
      attendance.changeLastProduct({ hasObservation: true });
      return `Ok, qual observação você deseja inserir?`;
    }

    attendance.incrementLastChildNumber();

    return this.chooseOptionsFinish(...arguments);
  }

  chooseOptionsFinish(option, observation) {
    if (attendance.getLastProduct().hasObservation) {
      attendance.changeLastProduct({ observation });
    }

    return super.chooseOptionsFinish(...arguments);
  }

  getOptions() {
    return [
      {
        name: "Sanduiche de presunto",
        value: "1",
      },
      {
        name: "Pão com ovo",
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
