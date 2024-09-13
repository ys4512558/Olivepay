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
import { unwriteReview } from '../component/review';

const ReviewPage = () => {
  const [unwriteReviews, setUnwriteReviews] = useAtom(unwriteReviewAtom);
  const [reviews, setReviews] = useAtom(reviewAtom);

  // 추후 유저 정보 조회랑 연결 또는 마이페이지에서 물려받기
  const memberId = 1;

  //   const queries = useQueries({
  //     queries: [
  //       {
  //         queryKey: ['review'],
  //         queryFn: () => getReviews(memberId),
  //       },
  //       {
  //         queryKey: ['review'],
  //         queryFn: () => getMissReviews(memberId),
  //       },
  //     ],
  //   });

  //   const [
  //     { data: reviewData, error: reviewError, isLoading: reviewLoading },
  //     {
  //       data: missReviewData,
  //       error: missReviewError,
  //       isLoading: missReviewLoading,
  //     },
  //   ] = queries;

  //   if (reviewData && missReviewData) {
  //     setReviews(reviewData);
  //     setUnwriteReviews(missReviewData);
  //   }

  //   if (reviewLoading || missReviewLoading) return <Loader />;

  //   if (reviewError || missReviewError) return <div>에러</div>;

  const handleDelete = (reviewId: number) => {
    deleteReview(reviewId);
  };

  return (
    <Layout className="px-8">
      <header className="mt-12 flex items-center justify-between">
        <BackButton />
        <PageTitle title="리뷰 관리" />
        <div className="w-8" />
      </header>
      <main className="mt-8">
        <section>
          {unwriteReviews.length > 0 && (
            <p className="border-b-2 border-DARKBASE pb-4 pl-2">
              아직 작성하지 않은 리뷰가 있어요 ❗
            </p>
          )}
          <div className="flex flex-col gap-4">
            {unwriteReviews.map((review) => {
              return (
                <div
                  className="flex items-center gap-4 border-b-2 border-dashed p-2"
                  key={review.franchise.id + review.createdAt}
                >
                  <div className="flex-1">
                    <div className="text-TERTIARY">
                      {review.createdAt.split(' ')[0]}
                    </div>
                    <div className="py-2 text-lg font-semibold">
                      {review.franchise.name}
                    </div>
                  </div>
                  <Button variant="text" label="작성하기" />
                </div>
              );
            })}
          </div>
        </section>
        <section className="mt-12">
          <p className="border-b-2 border-DARKBASE pb-4 pl-2 font-title text-xl">
            내가 쓴 리뷰
          </p>
          {reviews.map((review) => (
            <div key={review.reviewId}>
              <Card
                variant="review"
                title={review.franchise.name}
                score={review.stars}
                content={review.content}
                onClick={() => handleDelete(review.reviewId)}
              />
            </div>
          ))}
        </section>
      </main>
    </Layout>
  );
};

export default ReviewPage;
