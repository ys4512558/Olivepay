const QrView = ({ img }: QrViewProps) => {
  return (
    <div className="mb-12 flex h-[48dvh] justify-center">
      <img src={`data:image/png;base64,${img}`} className="h-auto w-full" />
    </div>
  );
};

export default QrView;
