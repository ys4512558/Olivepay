import clsx from 'clsx';

const Layout = ({ children, className }: LayoutProps) => {
  return (
    <div className="flex flex-col items-center bg-LIGHTBASE">
      <div
        className={clsx(
          'min-h-dvh w-full max-w-md overflow-hidden border-x-2 bg-white p-8',
          className,
        )}
      >
        {children}
      </div>
    </div>
  );
};

export default Layout;
