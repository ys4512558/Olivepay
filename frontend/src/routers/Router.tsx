import { Route, Routes } from 'react-router-dom';
import { Suspense, lazy } from 'react';
import PrivateRoute from './PrivateRoute';

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

const Router = () => {
  return (
    <Suspense fallback={<LoadingPage />}>
      <Routes>
        <Route path="/" element={<MainPage />} />
        <Route path="/signup" element={<SignupPage />} />
        <Route path="/login" element={<LoginPage />} />
        <Route
          path="/home"
          element={
            <PrivateRoute allowedRoles={['USER']}>
              <MyPage />
            </PrivateRoute>
          }
        />
        <Route
          path="/review"
          element={
            <PrivateRoute allowedRoles={['USER']}>
              <ReviewPage />
            </PrivateRoute>
          }
        />
        <Route
          path="/review/write/:franchiseId"
          element={
            <PrivateRoute allowedRoles={['USER']}>
              <ReviewWritePage />
            </PrivateRoute>
          }
        />
        <Route
          path="/franchise/home"
          element={
            <PrivateRoute allowedRoles={['OWNER']}>
              <MyStorePage />
            </PrivateRoute>
          }
        />
        <Route
          path="/franchise/qr"
          element={
            <PrivateRoute allowedRoles={['OWNER']}>
              <FranchiserQrPage />
            </PrivateRoute>
          }
        />
        <Route
          path="/franchise/coupon"
          element={
            <PrivateRoute allowedRoles={['OWNER']}>
              <FranchiserCouponPage />
            </PrivateRoute>
          }
        />
        <Route
          path="/franchise/income"
          element={
            <PrivateRoute allowedRoles={['OWNER']}>
              <FranchiserIncomePage />
            </PrivateRoute>
          }
        />
        <Route path="/card" element={<CardScanPage />} />
        <Route
          path="/pay"
          element={
            <PrivateRoute allowedRoles={['USER']}>
              <PayPage />
            </PrivateRoute>
          }
        />
        <Route
          path="/history"
          element={
            <PrivateRoute allowedRoles={['USER']}>
              <PayHistoryPage />
            </PrivateRoute>
          }
        />
        <Route path="/map" element={<MapPage />} />
        <Route
          path="/like"
          element={
            <PrivateRoute allowedRoles={['USER']}>
              <BookmarkPage />
            </PrivateRoute>
          }
        />
        <Route path="*" element={<NotFoundPage />} />
        <Route path="/donation-info" element={<DonationInfoPage />} />
        <Route path="/donate" element={<DonatePage />} />
        <Route path="/mydonation" element={<MyDonationPage />} />
      </Routes>
    </Suspense>
  );
};

export default Router;
