import { Route, Routes } from 'react-router-dom';
import { Suspense, lazy } from 'react';

const MainPage = lazy(() => import('../pages/MainPage'));
const SignupPage = lazy(() => import('../pages/SignupPage'));
const LoadingPage = lazy(() => import('../pages/LoadingPage'));
const NotFoundPage = lazy(() => import('../pages/NotFoundPage'));
const CardScanPage = lazy(() => import('../pages//CardScanPage'));

const Router = () => {
  return (
    <Suspense fallback={<LoadingPage />}>
      <Routes>
        <Route path="/" element={<MainPage />} />
        <Route path="/signup" element={<SignupPage />} />
        <Route path="*" element={<NotFoundPage />} />
        <Route path="/card" element={<CardScanPage />} />
      </Routes>
    </Suspense>
  );
};

export default Router;
