import { Button } from '../common';
import { DonateProps } from '../../types/donate';

const GetDonationList: React.FC<DonateProps> = ({ onNext }) => {
  return (
    <main className="p-10">
      <Button label="조회하기" variant="primary" onClick={onNext} />
    </main>
  );
};

export default GetDonationList;
