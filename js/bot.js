const attendance = new Attendance();
console.log(attendance);

const bot = {
  name: "Linguini",
  getFirstMessage() {
    const salutation = (() => {
      const hours = new Date().getHours();

      if (hours >= 5 && hours < 12) {
        return "Bom dia";
      }

      if (hours < 18) {
        return "Boa tarde";
      }

      return "Boa noite";
    })();

    return `🍕 ${salutation} ${attendance.customer.name}, sou ${
      this.name
    }, o chatbot do Rémy\`s Burger, estarei te atendendo agora 🍕
    Para começar digite a opção desejada:
    
    ${this.messages.map((message) => message.text).join("\n")}`;
  },
  respond(message) {
    if (!attendance.isStarted()) {
      attendance.start();
      attendance.addMessage({ message });

      return createMessage({
        myMessage: false,
        message: this.getFirstMessage(),
      });
    }

    return createMessage({
      myMessage: false,
      message: "Opção não implementada ainda",
    });
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
    },
  ],
};
