import Axios from '.';

const prefix = '/api/cards';

export const getCardsInfo = async () => {
  const response = await Axios(`${prefix}`);
  return response.data;
};


export const registerCard = async (
  realCardNumber : string,
  exporationYear: string,
  expirationMonth: string,
  cvc : string,
  creditPassword: string,
) => {
  const accessToken = localStorage.getItem('accessToken');
  const response = await Axios.post(`${prefix}`, {
    realCardNumber : realCardNumber,
    exporationYear: exporationYear,
    expirationMonth: expirationMonth,
    cvc : cvc,
    creditPassword: creditPassword,
  },
  {
    headers: {
      Authorization: `Bearer ${accessToken}`,
    },
});
return response.data
}