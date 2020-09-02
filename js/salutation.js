function getSalutation() {
  const hours = new Date().getHours();

  if (hours >= 5 && hours < 12) {
    return "Bom dia";
  }

  if (hours < 18) {
    return "Boa tarde";
  }

  return "Boa noite";
}
