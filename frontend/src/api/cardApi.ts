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
  const response = await Axios.post(`${prefix}`, {
    realCardNumber: realCardNumber,
    expirationYear: expirationYear,
    expirationMonth: expirationMonth,
    cvc: cvc,
    creditPassword: creditPassword,
  });
  return response.data;
};

export const readCardImage = async (image: Blob) => {
  const formData = new FormData();
  formData.append('cardImg', image);
  const response = await Axios.post(`/commons/ocr`, formData, {
    headers: {
      'Content-Type': 'multipart/form-data',
    },
  });
  return response.data;
};

export const removeCard = async (cardId: number) => {
  const response = await Axios.delete(`${prefix}/${cardId}`);
  return response.data;
};
