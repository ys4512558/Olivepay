import { ReactNode, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { useSnackbar } from 'notistack';

interface PrivateRouteProps {
  children: ReactNode;
  allowedRoles: string[];
  allowNoRole?: boolean;
}

const PrivateRoute = ({
  children,
  allowedRoles,
  allowNoRole = false,
}: PrivateRouteProps) => {
  const { enqueueSnackbar } = useSnackbar();
  const navigate = useNavigate();
  const role = localStorage.getItem('role');

  useEffect(() => {
    console.log('role:', role);
    if (role === 'TEMP_USER') {
      enqueueSnackbar('카드를 등록하고 서비스를 이용해주세요.', {
        variant: 'error',
      });

      setTimeout(() => {
        navigate(-1);
      }, 500);
    } else if (!role && !allowNoRole) {
      enqueueSnackbar('로그인이 필요합니다.', { variant: 'error' });

      setTimeout(() => {
        navigate('/login');
      }, 500);
    } else if (role && !allowedRoles.includes(role)) {
      enqueueSnackbar('잘못된 접근입니다.', { variant: 'error' });

      setTimeout(() => {
        navigate(-1);
      }, 500);
    }
  }, [role, allowedRoles, allowNoRole, navigate, enqueueSnackbar]);

  return <>{children}</>;
};

export default PrivateRoute;
