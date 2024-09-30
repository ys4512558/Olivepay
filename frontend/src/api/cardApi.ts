import Axios from '.';

const prefix = '/cards';

export const getCardsInfo = async () => {
  const response = await Axios(`${prefix}`);
  return response.data.data;
};
