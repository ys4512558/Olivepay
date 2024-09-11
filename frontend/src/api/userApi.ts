import Axios from './index';

const prefix = '/api/user/my';

export const getUsersInfo = async () => {
  const response = await Axios(`${prefix}`);
  return response.data;
};
