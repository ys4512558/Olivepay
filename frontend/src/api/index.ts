import axios from 'axios';
// import Cookies from 'js-cookie';

const Axios = axios.create({
  baseURL: import.meta.env.VITE_SERVER_URL,
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

Axios.interceptors.request.use((config) => {
  const token =
    'eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJkZWZhdWx0SXNzdWVyIiwicm9sZSI6IlVTRVIiLCJzdWIiOiIyIiwiaWF0IjoxNzI3NDEyOTkzLCJleHAiOjE3Mjc0OTkzOTN9.6zypx5qjwZlZYQHi96A2SJOShv6yupXIr6jki49f-w0';
  config.headers.Authorization = `Bearer ${token}`;
  // config.withCredentials = true;
  return config;
});

export default Axios;
