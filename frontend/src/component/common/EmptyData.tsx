const EmptyData: React.FC<EmptyDataProps> = ({ label }) => {
  return (
    <div className="flex flex-col items-center">
      <img src="no_data.svg" alt="no_data" className="size-44" />
      <p className="my-2 text-xl text-DARKBASE">{label}</p>
    </div>
  );
};

export default EmptyData;
