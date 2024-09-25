import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { franchise } from '../../types/franchise';
// import { toggleLike } from '../../api/franchiseApi';
import { BackButton, Card, Coupon, EmptyData, Button } from '../common';
import { HeartIcon as HeartSolidIcon } from '@heroicons/react/24/solid';
import { HeartIcon as HeartOutlineIcon } from '@heroicons/react/24/outline';
import { acquireCoupon } from '../../api/couponApi';
import { franchiseReviewAtom } from '../../atoms/reviewAtom';
import { useAtom } from 'jotai';

const FranchiseDetail: React.FC<{
  state: string;
  franchise: franchise;
  onClick: () => void;
}> = ({ franchise, onClick, state }) => {
  const navigate = useNavigate();
  const [reviews] = useAtom(franchiseReviewAtom);
  const [isLiked, setIsLiked] = useState(franchise.isLiked);
  const [likes, setLikes] = useState(franchise.likes);

  const handleLike = () => {
    // toggleLike(franchise.franchiseId);
    if (isLiked) {
      setLikes((prev) => prev - 1);
    } else {
      setLikes((prev) => prev + 1);
    }
    setIsLiked(!isLiked);
  };

  const handleDownloadCoupon = (couponUnit: number, franchiseId: number) => {
    acquireCoupon(couponUnit, franchiseId);
  };

  const handleDonateClick = () => {
    navigate('/donate', { state: { franchiseId: franchise.franchiseId } });
  };

  return (
    <section>
      <div className="flex justify-between">
        <BackButton onClick={onClick} />
        <h2 className="text-2xl font-semibold">{franchise.franchiseName}</h2>
        <div className="w-8" />
      </div>
      <div className="my-2 mt-6 flex items-center justify-between">
        <div className="flex items-center gap-4">
          <p>분류: {franchise.category}</p>
        </div>
        <div className="flex items-center gap-1">
          {isLiked ? (
            <HeartSolidIcon className="size-6 text-RED" onClick={handleLike} />
          ) : (
            <HeartOutlineIcon
              className="size-6 text-RED"
              onClick={handleLike}
            />
          )}
          <span>{likes}</span>
        </div>
      </div>
      <p>주소: {franchise.address}</p>

      {!state && (
        <div className="mt-6 flex flex-col items-center gap-4">
          <p className="text-lg">쿠폰 보유 현황</p>
          {franchise.coupon2 === 0 && franchise.coupon4 === 0 ? (
            <EmptyData label="미사용 쿠폰이 없습니다" />
          ) : (
            <>
              {franchise.coupon2 !== 0 && (
                <Coupon
                  storeName={franchise.franchiseName}
                  cost={2000}
                  count={franchise.coupon2}
                  onClick={() =>
                    handleDownloadCoupon(2000, franchise.franchiseId)
                  }
                />
              )}
              {franchise.coupon4 !== 0 && (
                <Coupon
                  storeName={franchise.franchiseName}
                  cost={4000}
                  count={franchise.coupon4}
                  onClick={() =>
                    handleDownloadCoupon(4000, franchise.franchiseId)
                  }
                />
              )}
            </>
          )}
        </div>
      )}

      <div className="mt-10">
        <p className="mb-2 text-center text-lg">가맹점 리뷰</p>
        {reviews.map((review) => (
          <Card
            variant="review"
            key={review.reviewId}
            title={review.memberName || ''}
            content={review.content}
            score={review.stars}
          />
        ))}
      </div>
      {state === 'donate' && (
        <Button
          label="후원하기"
          variant="primary"
          className="my-10"
          onClick={handleDonateClick}
        />
      )}
    </section>
  );
};

export default FranchiseDetail;
