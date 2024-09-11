import { Route, Routes } from 'react-router-dom';
import { Suspense, lazy } from 'react';

const MainPage = lazy(() => import('../pages/MainPage'));
const SignupPage = lazy(() => import('../pages/SignupPage'));
const MyPage = lazy(() => import('../pages/MyPage'));
const FranchiserCouponPage = lazy(
  () => import('../pages/franchiser/CouponPage'),
);
const FranchiserQrPage = lazy(() => import('../pages/franchiser/QrPage'));
const FranchiserIncomePage = lazy(
  () => import('../pages/franchiser/IncomePage'),
);
const MyStorePage = lazy(() => import('../pages/franchiser/MyStorePage'));
const LoadingPage = lazy(() => import('../pages/LoadingPage'));
const NotFoundPage = lazy(() => import('../pages/NotFoundPage'));

const Router = () => {
  return (
    <Suspense fallback={<LoadingPage />}>
      <Routes>
        <Route path="/" element={<MainPage />} />
        <Route path="/signup" element={<SignupPage />} />
        <Route path="/home" element={<MyPage />} />
        <Route path="/franchise/home" element={<MyStorePage />} />
        <Route path="/franchise/qr" element={<FranchiserQrPage />} />
        <Route path="/franchise/coupon" element={<FranchiserCouponPage />} />
        <Route path="/franchise/income" element={<FranchiserIncomePage />} />
        <Route path="*" element={<NotFoundPage />} />
      </Routes>
    </Suspense>
  );
};

export default Router;
