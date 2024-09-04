import { Route, Routes, Navigate } from 'react-router-dom';
import { Suspense, lazy } from 'react';

const Main = lazy(() => import('../pages/Main'));
const Signup = lazy(() => import('../pages/Signup'));

const Router = () => {
  return (
    <Suspense fallback={<div>로딩</div>}>
      <Routes>
        <Route path="/" element={<Main />} />
        <Route path="/signup" element={<Signup />} />
        <Route path="*" element={<Navigate to="/" replace />} />
      </Routes>
    </Suspense>
  );
};

export default Router;
