import ScrollToTop from './component/common/ScrollToTop';
import Router from './routers/Router';
import { SnackbarProvider } from 'notistack';
import {
  FaceSmileIcon,
  FaceFrownIcon,
  ExclamationTriangleIcon,
  InformationCircleIcon,
} from '@heroicons/react/24/solid';

function App() {
  return (
    <>
      <SnackbarProvider
        anchorOrigin={{
          vertical: 'bottom',
          horizontal: 'center',
        }}
        iconVariant={{
          success: <FaceSmileIcon className="mr-2 size-6" />,
          error: <FaceFrownIcon className="mr-2 size-6" />,
          warning: <ExclamationTriangleIcon className="mr-2 size-6" />,
          info: <InformationCircleIcon className="mr-2 size-6" />,
        }}
        autoHideDuration={1000}
      >
        <ScrollToTop />
        <Router />
      </SnackbarProvider>
    </>
  );
}

export default App;
