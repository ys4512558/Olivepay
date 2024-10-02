import Axios from '.';

const prefix = '/cards';

export const getCardsInfo = async () => {
  const response = await Axios(`${prefix}`);
  return response.data.data;
};

export const registerCard = async (
  realCardNumber: string,
  expirationYear: string,
  expirationMonth: string,
  cvc: string,
  creditPassword: string,
) => {
  const accessToken = localStorage.getItem('accessToken');
  const response = await Axios.post(
    `${prefix}`,
    {
      realCardNumber: realCardNumber,
      expirationYear: expirationYear,
      expirationMonth: expirationMonth,
      cvc: cvc,
      creditPassword: creditPassword,
    },
    {
      headers: {
        Authorization: `Bearer ${accessToken}`,
      },
    },
  );
  return response.data;
};
