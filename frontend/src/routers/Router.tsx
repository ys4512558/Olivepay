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
const CardScanPage = lazy(() => import('../pages//CardScanPage'));
const LoginPage = lazy(() => import('../pages/LoginPage'));
const DonationInfoPage = lazy(() => import('../pages/DonationInfoPage'));
const DonatePage = lazy(() => import('../pages/DonatePage'));
const MyDonationPage = lazy(() => import('../pages/MyDonationPage'));

const Router = () => {
  return (
    <Suspense fallback={<LoadingPage />}>
      <Routes>
        <Route path="/" element={<MainPage />} />
        <Route path="/signup" element={<SignupPage />} />
        <Route path="/login" element={<LoginPage />} />
        <Route path="/home" element={<MyPage />} />
        <Route path="/franchise/home" element={<MyStorePage />} />
        <Route path="/franchise/qr" element={<FranchiserQrPage />} />
        <Route path="/franchise/coupon" element={<FranchiserCouponPage />} />
        <Route path="/franchise/income" element={<FranchiserIncomePage />} />
        <Route path="*" element={<NotFoundPage />} />
        <Route path="/card" element={<CardScanPage />} />
        <Route path="/donation-info" element={<DonationInfoPage />} />
        <Route path="/donate" element={<DonatePage />} />
        <Route path="/mydonation" element={<MyDonationPage />} />
      </Routes>
    </Suspense>
  );
};

export default Router;
