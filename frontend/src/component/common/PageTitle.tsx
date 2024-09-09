const PageTitle: React.FC<PageTitleProps> = ({ title }) => {
  return (
    <header className="mt-4 text-center">
      <h1 className="text-xl font-bold">{title}</h1>
    </header>
  );
};

export default PageTitle;
