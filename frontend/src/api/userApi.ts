import Axios from './index';

export const getUsersInfo = async () => {
  const response = await Axios('');
  return response.data;
};
