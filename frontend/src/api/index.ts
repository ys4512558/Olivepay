import axios from 'axios';
import Cookies from 'js-cookie';
import { enqueueSnackbar } from 'notistack';

const Axios = axios.create({
  baseURL: import.meta.env.VITE_SERVER_URL,
  timeout: 10000,
  headers: {
    'Content-Type': 'application/json',
  },
  withCredentials: true,
});

Axios.interceptors.request.use((config) => {
  const token = localStorage.getItem('accessToken');
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

Axios.interceptors.response.use(
  (response) => {
    return response;
  },
  async (error) => {
    const originalRequest = error.config;
    if (
      error.response &&
      error.response.status === 401 &&
      !originalRequest._retry
    ) {
      originalRequest._retry = true;

      try {
        const accessToken = localStorage.getItem('accessToken');
        const refreshToken = Cookies.get('refreshToken');

        const { data } = await axios.post(
          `${import.meta.env.VITE_SERVER_URL}/auth/refresh`,
          {
            accessToken,
            refreshToken,
          },
        );

        localStorage.setItem('accessToken', data.accessToken);
        localStorage.setItem('refreshToken', data.refreshToken);

        originalRequest.headers.Authorization = `Bearer ${data.accessToken}`;
        return Axios(originalRequest);
      } catch {
        enqueueSnackbar('로그인이 만료되었습니다. 다시 로그인해주세요.', {
          variant: 'info',
        });
        window.location.href = '/login';
      }
    }

    return Promise.reject(error);
  },
);

export default Axios;
