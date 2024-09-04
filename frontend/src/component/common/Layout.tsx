import clsx from 'clsx';

const Layout = ({ children, className }: LayoutProps) => {
  return (
    <div className="bg-LIGHTBASE flex flex-col items-center">
      <div
        className={clsx(
          'bg-WHITE min-h-dvh w-full max-w-md overflow-hidden border-x-2 p-8',
          className,
        )}
      >
        {children}
      </div>
    </div>
  );
};

export default Layout;
