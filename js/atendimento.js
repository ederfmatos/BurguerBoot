class Attendance {
  constructor() {
    this.started = false;
    this.finished = false;
    this.customer = { name: "Eder Matos" };
    this.messages = [];
    this.lastChildAction = 0;
    this.products = [];
  }

  start() {
    this.createdAt = new Date().getTime();
    this.started = true;
    this.finished = false;
    this.messages = [];
    this.lastMessage = null;
  }

  addMessage(message) {
    this.messages.push(message);
  }

  isStarted() {
    return Boolean(this.started) && this.finished === false;
  }

  getMessages() {
    if (this.messages.length === 0) {
      return [];
    }

    return [...this.messages.slice(1, this.messages.length)];
  }

  getLastMessage() {
    return this.lastMessage;
  }

  setLastMessage(lastMessage) {
    this.lastMessage = lastMessage;
  }

  incrementLastChildNumber() {
    this.lastChildAction++;
  }

  finish() {
    this.finished = true;
    this.finishedAt = new Date().getTime();
  }

  addNewProduct(product) {
    this.products.push(product);
  }

  changeLastProduct(attributes) {
    const lastProduct = this.getLastProduct();

    if (!lastProduct) return;

    this.products[this.products.length - 1] = {
      ...lastProduct,
      ...attributes,
    };
  }

  getLastProduct() {
    return { ...this.products[this.products.length - 1] };
  }

  hasProducts() {
    return this.products.length > 0;
  }

  getAttendanceInformation() {
    const formatter = new Intl.NumberFormat([], {
      style: "currency",
      currency: "BRL",
    });

    return `Informações do atendimento:
    
    Iniciado às ${new Date(this.createdAt).toLocaleTimeString()}

    Itens:

    ${this.products
      .map(
        (product) =>
          `
    Pedido: ${product.name}
    Quantidade: ${product.quantity}
    Valor: ${formatter.format(product.price * product.quantity)}
    ${product.hasObservation ? `Observação: ${product.observation}` : ""}`
      )
      .map((text) => text.trim())
      .join("\n\n")}

    Valor total do pedido: ${formatter.format(
      this.products
        .map(({ price, quantity }) => price * quantity)
        .reduce((accum, price) => accum + price, 0)
    )}
    Tempo de espera: 40 minutos, mas fique tranquilo, estarei entrando em contato se for preciso
    `;
  }
}
