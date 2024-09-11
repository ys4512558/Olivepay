import Axios from '.';

const prefix = '/api/cards';

export const getCardsInfo = async () => {
  const response = await Axios(`${prefix}`);
  return response.data;
};
