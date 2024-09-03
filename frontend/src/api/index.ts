import axios from 'axios';
// import Cookies from 'js-cookie';

const Axios = axios.create({
  baseURL: 'https://jsonplaceholder.typicode.com/users',
  timeout: 10000,
  headers: {
    'Content-Type': 'application/json',
  },
});

/*
Axios.interceptors.request.use(
  (config) => {
    const token = Cookies.get('token');
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  },
);
*/

export default Axios;
