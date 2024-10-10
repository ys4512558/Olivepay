import { Link } from 'react-router-dom';
import { Card } from '../common';
import { Donate } from '../../types/donate';

const MyDonationList: React.FC<Donate> = ({ donationList }) => {
  return (
    <main className="px-10 py-2">
      <figure className="flex flex-col gap-4">
        {donationList.map((el) => (
          <Card
            key={el.franchiseId}
            variant="donation"
            title={el.franchiseName}
            location={el.address}
            date={el.date}
            price={el.money}
          />
        ))}
      </figure>

      <figure className="mb-28 me-3 mt-5 items-center text-right text-base">
        <Link to="/donation-info" className="hover:text-blue-700">
          🏃‍♀️ 후원정보페이지로 돌아가기
        </Link>
      </figure>
    </main>
  );
};

export default MyDonationList;
