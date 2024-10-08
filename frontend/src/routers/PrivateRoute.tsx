import { ReactNode, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { useSnackbar } from 'notistack';

interface PrivateRouteProps {
  children: ReactNode;
  allowedRoles: string[];
}

const PrivateRoute = ({ children, allowedRoles }: PrivateRouteProps) => {
  const { enqueueSnackbar } = useSnackbar();
  const navigate = useNavigate();
  const role = localStorage.getItem('role');

  useEffect(() => {
    if (!allowedRoles.includes(role || '')) {
      enqueueSnackbar('올바른 접근이 아닙니다.', { variant: 'error' });

      setTimeout(() => {
        navigate(-1);
      }, 500);
    }
  }, [allowedRoles, role, navigate, enqueueSnackbar]);

  return <>{children}</>;
};

export default PrivateRoute;
