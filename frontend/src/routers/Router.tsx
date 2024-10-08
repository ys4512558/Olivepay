import { Route, Routes } from 'react-router-dom';
import { Suspense, lazy } from 'react';

const MainPage = lazy(() => import('../pages/MainPage'));
const SignupPage = lazy(() => import('../pages/SignupPage'));
const MyPage = lazy(() => import('../pages/MyPage'));
const PayPage = lazy(() => import('../pages/PayPage'));
const PayHistoryPage = lazy(() => import('../pages/PayHistoryPage'));
const ReviewPage = lazy(() => import('../pages/ReviewPage'));
const ReviewWritePage = lazy(() => import('../pages/ReviewWritePage'));
const FranchiserCouponPage = lazy(
  () => import('../pages/franchise/CouponPage'),
);
const FranchiserQrPage = lazy(() => import('../pages/franchise/QrPage'));
const FranchiserIncomePage = lazy(
  () => import('../pages/franchise/IncomePage'),
);
const MyStorePage = lazy(() => import('../pages/franchise/MyStorePage'));
const LoadingPage = lazy(() => import('../pages/LoadingPage'));
const NotFoundPage = lazy(() => import('../pages/NotFoundPage'));
const CardScanPage = lazy(() => import('../pages//CardScanPage'));
const LoginPage = lazy(() => import('../pages/LoginPage'));
const MapPage = lazy(() => import('../pages/MapPage'));
const BookmarkPage = lazy(() => import('../pages/BookmarkPage'));
const DonationInfoPage = lazy(() => import('../pages/DonationInfoPage'));
const DonatePage = lazy(() => import('../pages/DonatePage'));
const MyDonationPage = lazy(() => import('../pages/MyDonationPage'));
const OCR = lazy(() => import('../component/signup/OCR'));

const Router = () => {
  return (
    <Suspense fallback={<LoadingPage />}>
      <Routes>
        <Route path="/" element={<MainPage />} />
        <Route path="/signup" element={<SignupPage />} />
        <Route path="/login" element={<LoginPage />} />
        <Route path="/home" element={<MyPage />} />
        <Route path="/review" element={<ReviewPage />} />
        <Route
          path="/review/write/:franchiseId"
          element={<ReviewWritePage />}
        />
        <Route path="/franchise/home" element={<MyStorePage />} />
        <Route path="/franchise/qr" element={<FranchiserQrPage />} />
        <Route path="/franchise/coupon" element={<FranchiserCouponPage />} />
        <Route path="/franchise/income" element={<FranchiserIncomePage />} />
        <Route path="/card" element={<CardScanPage />} />
        <Route path="/card/ocr" element={<OCR />} />
        <Route path="/pay" element={<PayPage />} />
        <Route path="/history" element={<PayHistoryPage />} />
        <Route path="/map" element={<MapPage />} />
        <Route path="/like" element={<BookmarkPage />} />
        <Route path="*" element={<NotFoundPage />} />
        <Route path="/donation-info" element={<DonationInfoPage />} />
        <Route path="/donate" element={<DonatePage />} />
        <Route path="/mydonation" element={<MyDonationPage />} />
      </Routes>
    </Suspense>
  );
};

export default Router;
