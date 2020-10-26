import express from 'express';
import App from './app';

const app = express();

const port = process.env.PORT || 4000;

app.listen(port, () => {
  const app = new App();
  app.start();
});
