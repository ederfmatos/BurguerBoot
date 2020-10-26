export function getFileInBase64(filename) {
  return new Promise((resolve, reject) => {
    try {
      filename = join(process.cwd(), filename);
      const fileMime = mime.getType(filename);

      const file = readFileSync(filename, { encoding: 'base64' });
      resolve(`data:${fileMime};base64,${file}`);
    } catch (error) {
      reject(error);
    }
  });
}

export function delay(ms) {
  return new Promise(resolve => {
    setTimeout(resolve, ms);
  });
}
