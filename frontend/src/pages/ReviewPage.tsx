import { useNavigate } from 'react-router-dom';
import { useAtom } from 'jotai';
import { useQueries } from '@tanstack/react-query';
import { reviewAtom, unwriteReviewAtom } from '../atoms/reviewAtom';
import { getReviews, getMissReviews, deleteReview } from '../api/reviewApi';

import {
  Layout,
  BackButton,
  PageTitle,
  Button,
  Card,
  Loader,
} from '../component/common';
import { useState, useEffect } from 'react';
import { formatDate } from '../utils/dateUtils';
import { Helmet } from 'react-helmet';

const ReviewPage = () => {
  const navigate = useNavigate();
  const [unwriteReviews, setUnwriteReviews] = useAtom(unwriteReviewAtom);
  const [reviews, setReviews] = useAtom(reviewAtom);
  const [reviewIndex, setReviewIndex] = useState<number>(0);
  // const [missReviewIndex, setMissReviewIndex] = useState<number>(0);
  const [hasMore, setHasMore] = useState(true);

  const queries = useQueries({
    queries: [
      {
        queryKey: ['review'],
        queryFn: () => getReviews(reviewIndex),
        staleTime: 1000 * 60 * 5,
      },
      {
        queryKey: ['availableReview'],
        queryFn: () => getMissReviews(),
      },
    ],
  });

  const [
    {
      data: reviewData,
      error: reviewError,
      isLoading: reviewLoading,
      isSuccess: reviewSuccess,
    },
    {
      data: missReviewData,
      error: missReviewError,
      isLoading: missReviewLoading,
      isSuccess: missReviewSuccess,
    },
  ] = queries;

  useEffect(() => {
    if (missReviewSuccess && missReviewData) {
      setUnwriteReviews(missReviewData);
    }
  }, [missReviewData, missReviewSuccess, setUnwriteReviews]);

  useEffect(() => {
    if (reviewSuccess && reviewData) {
      setReviews(reviewData.reviews);
      setReviewIndex(reviewData.nextIndex);
      setHasMore(reviewData.reviews.length >= 20);
    }
  }, [reviewData, reviewSuccess, setReviews]);

  if (reviewLoading || missReviewLoading) return <Loader />;

  if (reviewError || missReviewError) return <div>에러</div>;

  const handleLoadMore = async () => {
    const result = await getReviews(reviewIndex);
    if (result.reviews.length < 20) {
      setHasMore(false);
    }
    setReviewIndex(result.nextIndex);
    setReviews((prev) => [...prev, ...result.reviews]);
  };

  const handleNavigateToWriteReview = (
    franchiseId: number,
    franchiseName: string,
    createdAt: string,
  ) => {
    navigate(`/review/write/${franchiseId}`, {
      state: {
        franchiseName: franchiseName,
        createdAt: createdAt,
      },
    });
  };

  const handleDelete = (reviewId: number) => {
    deleteReview(reviewId);
    setReviews((prevReviews) =>
      prevReviews.filter((review) => review.reviewId !== reviewId),
    );
  };

  return (
    <>
      <Helmet>
        <meta
          name="description"
          content="결식 아동이 자신이 작성한 리뷰를 조회하고 삭제할 수 있으며 작성하지 않은 리뷰에 대해 작성할 수 있습니다."
        />
      </Helmet>
      <Layout className="px-8">
        <header className="mt-4 flex items-center justify-between">
          <BackButton />
          <PageTitle title="리뷰 관리" />
          <div className="w-8" />
        </header>
        <main className="mt-4">
          <section>
            {unwriteReviews?.length > 0 && (
              <p className="border-b-2 border-DARKBASE pb-4 pl-2 text-base">
                아직 작성하지 않은 리뷰가 있어요 ❗
              </p>
            )}
            <div className="flex flex-col">
              {unwriteReviews?.map((review) => {
                return (
                  <div
                    className="flex items-center gap-4 border-b-2 border-dashed p-2 text-base"
                    key={review.franchise.id + review.createdAt}
                  >
                    <div className="flex-1">
                      <div className="text-TERTIARY">
                        {formatDate(review.createdAt)}
                      </div>
                      <div className="text-md font-semibold">
                        {review.franchise.name}
                      </div>
                    </div>
                    <Button
                      variant="text"
                      label="작성하기"
                      onClick={() =>
                        handleNavigateToWriteReview(
                          review.franchise.id,
                          review.franchise.name,
                          review.createdAt,
                        )
                      }
                    />
                  </div>
                );
              })}
            </div>
          </section>
          <section className="mb-20 mt-4">
            <p className="mb-2 border-b-2 border-DARKBASE p-2 font-title text-md">
              📝 내가 쓴 리뷰
            </p>
            {reviews?.map((review) => (
              <div key={review.reviewId}>
                <Card
                  variant="review"
                  title={review.franchise?.name || ''}
                  score={review.stars}
                  content={review.content}
                  onClick={() => handleDelete(review.reviewId)}
                />
              </div>
            ))}
            <div className="mt-4 text-center">
              {hasMore && (
                <Button
                  label="더보기"
                  variant="secondary"
                  onClick={handleLoadMore}
                />
              )}
            </div>
          </section>
        </main>
      </Layout>
    </>
  );
};

export default ReviewPage;
