const attendance = new Attendance();

const bot = {
  name: "Linguini",
  merchantName: "Rémy`s Burger",
  getFirstMessage() {
    return `🍕 ${getSalutation()} ${attendance.customer.name}, sou ${
      this.name
    }, o chatbot do ${this.merchantName}, estarei te atendendo agora 🍕
    Para começar digite a opção desejada:
    
    ${this.formatOptions(this.messages)}`;
  },
  formatOptions(options) {
    return options
      .map(({ name, value }) => `${String(value).padStart(2, "0")} - ${name}`)
      .join("\n");
  },
  respond(message) {
    if (!attendance.isStarted()) {
      attendance.start();
      attendance.addMessage({ message });

      return this.createBotMessage(this.getFirstMessage());
    }

    attendance.addMessage({ message });

    const response = this.getResponseFromMessage({ message });
    return this.createBotMessage(response);
  },
  createBotMessage(message) {
    return createMessage({
      myMessage: false,
      message,
    });
  },
  getOptionFromMessage(message, messages = this.messages) {
    const option = messages.find(
      ({ value }) => value === String(message).trim()
    );

    if (!option) {
      throw new InvalidOptionError();
    }

    return option;
  },
  asksToChooseAnOption(options) {
    return `Escolha uma opção:
  
    ${this.formatOptions(options)}`;
  },
  getResponseFromMessage({ message }) {
    try {
      if (attendance.getLastMessage() === null) {
        const response = this.getOptionFromMessage(message);

        attendance.setLastMessage(response);

        if (response.options) {
          return this.asksToChooseAnOption(response.options);
        }

        if (response.onSelect) {
          return response.onSelect();
        }

        throw new OptionNotImplementedError();
      }

      const lastMessage = attendance.getLastMessage();

      if (lastMessage.actions) {
        return this.handleOptionActions(lastMessage, message);
      }

      const response = this.getOptionFromMessage(message, lastMessage.options);

      attendance.setLastMessage(response);

      if (response.options) {
        return this.asksToChooseAnOption(response.options);
      }

      if (response.onSelect) {
        return response.onSelect(response);
      }

      if (response.actions) {
        return this.handleOptionActions(response, message);
      }

      throw new OptionNotImplementedError();
    } catch (error) {
      if (error instanceof BotError) {
        error.showError();
        return error.message;
      }

      console.error(error);
      return "Ocorreu um erro desconhecido";
    }
  },
  handleOptionActions(option, message) {
    const actionResponse = option.actions[attendance.lastChildAction](
      option,
      message,
      attendance
    );
    attendance.incrementLastChildNumber();

    return actionResponse;
  },
  finishAttendance() {
    attendance.finish();

    return `Finalizando atendimento, muito obrigado por entrar em contato com ${
      this.merchantName
    }. ${getSalutation()} 🖖🍴`;
  },
};

bot.messages = [
  {
    name: "Fazer pedido",
    value: "1",
    options: [
      new Snack(bot).getRequestObject(),
      new Drinks(bot).getRequestObject(),
    ],
  },
  {
    name: "Saber andamento de pedido",
    value: "2",
  },
  {
    name: "Finalizar atendimento",
    value: "3",
    globalAction: "finishAttendance",
  },
];

function configureBot() {
  bot.messages.forEach(configureParents);
}

function configureParents(message) {
  if (message.options) {
    const { onSelectChild } = message;

    message.options.forEach((option) => {
      configureParents(option);

      if (onSelectChild) {
        onSelectChild.forEach((action) => {
          if (!option.actions) {
            option.actions = [];
          }

          option.actions.push(action);
        });
      }
    });

    delete message.onSelectChild;
  }

  if (message.globalAction && typeof bot[message.globalAction] === "function") {
    message.onSelect = bot[message.globalAction].bind(bot);
    delete message.globalAction;
  }
}

configureBot();
