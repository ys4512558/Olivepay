interface QrViewProps {
  img: string;
}

const QrView = ({ img }: QrViewProps) => {
  return <img src={'/example.PNG'} />;
};

export default QrView;
