const EmptyData: React.FC<EmptyDataProps> = ({ label }) => {
  return (
    <div className="mt-4 flex flex-col items-center gap-6">
      <img src="/no_data.svg" alt="no_data" className="size-44" />
      <p className="text-lg text-DARKBASE">{label}</p>
    </div>
  );
};

export default EmptyData;
