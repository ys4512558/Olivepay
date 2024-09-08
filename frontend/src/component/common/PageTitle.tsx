const PageTitle: React.FC<PageTitleProps> = ({ title }) => {
    return (
      <div className="absolute top-10 left-1/2 transform -translate-x-1/2 mt-4 flex flex-col text-center">
        <h1 className="text-xl font-bold">{title}</h1>
      </div>
    );
  };
  
export default PageTitle;  