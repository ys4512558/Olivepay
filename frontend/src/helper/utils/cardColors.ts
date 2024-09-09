const getCardBackground = (cardName: string): string => {
  const prefix = cardName.slice(0, 2);
  switch (prefix) {
    case '꿈나':
      return 'DT';
    case '신한':
      return 'SH';
    case '우리':
      return 'WR';
    case '하나':
      return 'HN';
    case '국민':
      return 'KM';
    default:
      return 'linear-gradient(45deg, #ffffff, #cccccc)';
  }
};

export default getCardBackground;
