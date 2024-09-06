import { Route, Routes, Navigate } from 'react-router-dom';
import { Suspense, lazy } from 'react';

import { LoadingPage } from '../pages';

const Main = lazy(() => import('../pages/MainPage'));
const Signup = lazy(() => import('../pages/SignupPage'));

const Router = () => {
  return (
    <Suspense fallback={<LoadingPage />}>
      <Routes>
        <Route path="/" element={<Main />} />
        <Route path="/signup" element={<Signup />} />
        <Route path="*" element={<Navigate to="/" replace />} />
      </Routes>
    </Suspense>
  );
};

export default Router;
