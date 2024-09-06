import clsx from 'clsx';

import { BottomTab } from './BottomTab';

const Layout = ({ children, className, hasBottomTab = true }: LayoutProps) => {
  return (
    <div className="flex flex-col items-center bg-LIGHTBASE">
      <div
        className={clsx(
          'min-h-dvh w-full max-w-md overflow-hidden border-x-2 bg-white',
          className,
        )}
      >
        {children}
      </div>
      {hasBottomTab && <BottomTab />}
    </div>
  );
};

export default Layout;
