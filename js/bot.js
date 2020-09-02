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
    return options.map(({ text }) => text).join("\n");
  },
  respond(message) {
    if (!attendance.isStarted()) {
      attendance.start();
      attendance.addMessage({ message });

      return this.createBotMessage(this.getFirstMessage());
    }

    attendance.addMessage({ message });

    const response = this.getResponseFromMessage({ message });

    if (typeof response === "string") {
      return this.createBotMessage(response);
    }

    if (Array.isArray(response)) {
      return response.forEach(createBotMessage);
    }
  },
  createBotMessage(message) {
    return createMessage({
      myMessage: false,
      message,
    });
  },
  getResponseFromMessage({ message }) {
    try {
      if (attendance.getLastMessage() === null) {
        const response = this.messages.find(
          ({ value }) => value === String(message).trim()
        );

        if (!response) {
          throw new OptionNotImplementedError();
        }

        if (response.options) {
          return `Escolha uma opção:
  
          ${this.formatOptions(response.options)}`;
        }

        if (response.globalAction) {
          return this[response.globalAction]();
        }

        throw new OptionNotImplementedError();
      }

      return "Opção não implementada ainda men";
    } catch (error) {
      if (error instanceof BotError) {
        error.showError();
        return error.message;
      }

      console.error(error);
      return "Ocorreu um erro desconhecido";
    }
  },
  finishAttendance() {
    return `Finalizando atendimento, muito obrigado por entrar em contato com ${
      this.merchantName
    }. ${getSalutation()} 🖖🍴`;
  },
  messages: [
    {
      text: "01 - Fazer pedido",
      value: "1",
      options: [
        {
          text: "01 - Lanches",
          value: "1.1",
          options: [
            {
              text: "Sanduiche de presunto",
              value: "1.1.1",
            },
            {
              text: "Pão com ovo",
              value: "1.1.1",
            },
          ],
        },
        {
          text: "02 - Bebidas",
          value: "1.2",
          options: [
            {
              text: "Coca cola 600ml",
              value: "1.2.1",
            },
            {
              text: "Fanta Guaraná",
              value: "1.2.2",
            },
          ],
        },
      ],
    },
    {
      text: "02 - Saber andamento de pedido",
      value: "2",
    },
    {
      text: "03 - Finalizar atendimento",
      value: "3",
      globalAction: "finishAttendance",
    },
  ],
};
