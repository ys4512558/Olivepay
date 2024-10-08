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

let isRefreshing = false;
let refreshSubscribers: ((token: string) => void)[] = [];

function onRefreshed(token: string) {
  refreshSubscribers.map((callback) => callback(token));
  refreshSubscribers = [];
}

Axios.interceptors.response.use(
  (response) => response,
  async (error) => {
    const originalRequest = error.config;

    if (
      error.response &&
      error.response.status === 401 &&
      !originalRequest._retry
    ) {
      if (isRefreshing) {
        return new Promise((resolve) => {
          refreshSubscribers.push((token) => {
            originalRequest.headers.Authorization = `Bearer ${token}`;
            resolve(Axios(originalRequest));
          });
        });
      }

      originalRequest._retry = true;
      isRefreshing = true;

      try {
        const refreshToken = Cookies.get('refreshToken');
        const accessToken = localStorage.getItem('accessToken');

        const { data } = await axios.post(
          `${import.meta.env.VITE_SERVER_URL}/auths/refresh`,
          { accessToken, refreshToken },
        );

        localStorage.setItem('accessToken', data.data.data.accessToken);
        Cookies.set('refreshToken', data.data.data.refreshToken);

        Axios.defaults.headers.Authorization = `Bearer ${data.data.data.accessToken}`;

        onRefreshed(data.data.data.accessToken);

        originalRequest.headers.Authorization = `Bearer ${data.data.data.accessToken}`;

        return Axios(originalRequest);
      } catch {
        enqueueSnackbar('로그인이 만료되었습니다. 다시 로그인해주세요.', {
          variant: 'info',
        });
        localStorage.clear();
        Cookies.remove('refreshToken');
      } finally {
        isRefreshing = false;
      }
    }

    if (error.response && error.response.status === 403) {
      enqueueSnackbar('권한이 없는 페이지입니다.', { variant: 'error' });
      window.history.back();
    }

    return Promise.reject(error);
  },
);

export default Axios;
