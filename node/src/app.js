import 'dotenv/config';
import puppeteer from 'puppeteer';
import { join } from 'path';
import qrcode from 'qrcode-terminal';

import './welcome';
import Logger from './logger';
import { delay } from './utils';
import * as constants from './constants';

export default class App {
  async start() {
    await this.downloadAndStartThings();

    await this.checkLogin();
  }

  async downloadAndStartThings() {
    try {
      Logger.start('Iniciando aplicação');

      const browser = await puppeteer.launch({
        headless: false,
        userDataDir: join(process.cwd(), 'ChromeSession'),
        devtools: false,
        args: [...constants.DEFAULT_CHROMIUM_ARGS],
        userDataDir: constants.DEFAULT_DATA_DIR,
        defaultViewport: null,
      });

      Logger.stop('Iniciando aplicação ... terminado!');

      Logger.start('Abrindo WhatsApp');
      this.page = await browser.pages();

      if (this.page.length > 0) {
        this.page = this.page[0];
        this.page.setBypassCSP(true);

        this.page.setUserAgent(
          'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36'
        );

        await this.page.goto('https://web.whatsapp.com', {
          waitUntil: 'networkidle0',
          timeout: 0,
        });

        Logger.stop('WhatsApp aberto com sucesso!');
        this.page.exposeFunction('log', message => console.log(message));
      }
    } catch (error) {
      console.log(error);
    }
  }

  async injectScripts() {
    await this.page.waitForSelector('[data-icon=laptop]');

    let filepath = join(__dirname, 'injection', 'WAPI.js');

    await this.page.addScriptTag({ path: require.resolve(filepath) });

    filepath = join(__dirname, 'injection', 'index.js');

    await this.page.addScriptTag({ path: require.resolve(filepath) });

    return Promise.resolve();
  }

  async checkLogin() {
    Logger.start('Carregando página');

    await delay(10000);

    let isLogged = await this.page.evaluate("localStorage['last-wid']");

    if (isLogged) {
      Logger.stop('Você já está logado');
      await this.injectScripts();
    } else {
      Logger.info('Você não está logado! Por favor, leia o QRCode a seguir.');
      await this.getAndShowQR();
    }

    return Promise.resolve();
  }

  async getAndShowQR() {
    const scanme = "img[alt='Scan me!'], canvas";
    await this.page.waitForSelector(scanme);

    const imageData = await this.page.evaluate(
      `document.querySelector("${scanme}").parentElement.getAttribute("data-ref")`
    );

    qrcode.generate(imageData, { small: true });
    Logger.start('Esperando leitura do QRCode');

    let isLoggedIn = await this.injectScripts();
    while (!isLoggedIn) {
      await delay(300);
      isLoggedIn = await this.injectScripts();
    }

    if (isLoggedIn) {
      Logger.stop('Boooa agora você está logado!');
    }
  }
}
