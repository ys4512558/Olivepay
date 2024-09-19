interface QrViewProps {
  img: string;
}

interface QrScanProps {
  onResult: (result: string) => void;
}
