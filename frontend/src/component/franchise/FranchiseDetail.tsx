import { useEffect, useState, useCallback } from 'react';
import { useNavigate } from 'react-router-dom';
import { useQuery } from '@tanstack/react-query';
import { franchise, franchiseCategory } from '../../types/franchise';
import { toggleLike } from '../../api/franchiseApi';
import { BackButton, Card, Coupon, EmptyData, Button, Loader } from '../common';
import { HeartIcon as HeartSolidIcon } from '@heroicons/react/24/solid';
import { HeartIcon as HeartOutlineIcon } from '@heroicons/react/24/outline';
import { acquireCoupon } from '../../api/couponApi';
import { franchiseReviewAtom } from '../../atoms/reviewAtom';
import { useAtom } from 'jotai';
import { getFranchiseReview } from '../../api/reviewApi';
import { enqueueSnackbar } from 'notistack';

const FranchiseDetail: React.FC<{
  state: string;
  franchise: franchise;
  onClick: () => void;
}> = ({ franchise, onClick, state }) => {
  const navigate = useNavigate();
  const [reviews, setReviews] = useAtom(franchiseReviewAtom);
  const [isLiked, setIsLiked] = useState(franchise.isLiked);
  const [index, setIndex] = useState<number>(0);
  const [hasMore, setHasMore] = useState<boolean>(true);

  const { data, error, isLoading, isSuccess, refetch } = useQuery({
    queryKey: ['franchiseReview', franchise.franchiseId],
    queryFn: () => {
      return index
        ? getFranchiseReview(franchise.franchiseId, index)
        : getFranchiseReview(franchise.franchiseId);
    },
    staleTime: 1000 * 60 * 5,
  });

  useEffect(() => {
    if (franchise.franchiseId) {
      refetch();
    }
  }, [franchise.franchiseId, refetch]);

  useEffect(() => {
    if (data && isSuccess) {
      setReviews(data.contents);
      setIndex(data.nextIndex);
      setHasMore(data.contents?.length >= 20);
    }
  }, [data, isSuccess, setReviews, setIndex, setHasMore]);

  const handleLike = useCallback(() => {
    toggleLike(franchise.franchiseId);
    setIsLiked((prev) => !prev);
  }, [franchise.franchiseId]);

  const handleDownloadCoupon = useCallback(
    async (couponUnit: number, franchiseId: number) => {
      try {
        await acquireCoupon(couponUnit, franchiseId);
        enqueueSnackbar('쿠폰 다운로드가 완료되었습니다.', {
          variant: 'success',
        });
      } catch (err) {
        if (err instanceof Error) {
          enqueueSnackbar(err.message, {
            variant: 'error',
          });
        }
      }
    },
    [],
  );

  const handleDonateClick = useCallback(() => {
    navigate('/donate', { state: { franchiseId: franchise.franchiseId } });
  }, [navigate, franchise.franchiseId]);

  const getFranchiseCategoryLabel = useCallback(
    (category: franchiseCategory | string) => {
      return (
        franchiseCategory[category as keyof typeof franchiseCategory] || '기타'
      );
    },
    [],
  );

  const handleLoadMore = useCallback(async () => {
    const result = await getFranchiseReview(franchise.franchiseId, index);
    if (result.reviews.length < 20) {
      setHasMore(false);
    }
    setIndex(result.nextIndex);
    setReviews((prev) => [...prev, ...result.contents]);
  }, [franchise.franchiseId, index, setReviews]);

  if (isLoading) return <Loader />;

  if (error) return <div>리뷰 목록 로딩 실패</div>;

  return (
    <section>
      <div className="flex items-center justify-between">
        <BackButton onClick={onClick} />
        <h2 className="text-lg font-semibold">{franchise.franchiseName}</h2>
        <div className="w-8" />
      </div>
      <div className="my-2 mt-6 flex items-center justify-between text-base">
        <div className="flex items-center gap-4">
          <p>분류: {getFranchiseCategoryLabel(franchise.category)}</p>
        </div>
        {!state ||
          (localStorage.getItem('role') === 'OWNER' && (
            <div className="flex items-center gap-1">
              {isLiked ? (
                <HeartSolidIcon
                  className="size-6 text-RED"
                  onClick={handleLike}
                />
              ) : (
                <HeartOutlineIcon
                  className="size-6 text-RED"
                  onClick={handleLike}
                />
              )}
            </div>
          ))}
      </div>
      <p className="text-base">주소: {franchise.address}</p>

      {!state && (
        <div className="mt-4 flex flex-col items-center gap-4">
          <p className="text-md font-semibold">쿠폰 보유 현황</p>
          {franchise.coupon2 === 0 && franchise.coupon4 === 0 ? (
            <EmptyData label="사용 가능한 쿠폰이 없습니다." />
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

      <div className="mb-12 mt-8">
        <p className="mb-2 text-center text-md font-semibold">가맹점 리뷰</p>
        {reviews?.length === 0 && <EmptyData label="리뷰가 없습니다." />}
        {reviews?.map((review) => (
          <Card
            variant="review"
            key={review.reviewId}
            title={review.memberName || ''}
            content={review.content}
            stars={review.stars}
          />
        ))}
        <div className="mt-2 text-center">
          {hasMore && (
            <Button
              label="더보기"
              variant="secondary"
              onClick={handleLoadMore}
            />
          )}
        </div>
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
