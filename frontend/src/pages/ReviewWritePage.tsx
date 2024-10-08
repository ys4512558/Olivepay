import { useState } from 'react';
import { useLocation, useParams, useNavigate } from 'react-router-dom';
import {
  Layout,
  BackButton,
  PageTitle,
  StarRating,
  Button,
} from '../component/common';
import { writeReview } from '../api/reviewApi';
import { enqueueSnackbar } from 'notistack';
import { Helmet } from 'react-helmet';
import { useAtom } from 'jotai';
import { userAtom } from '../atoms';

const ReviewWritePage = () => {
  const location = useLocation();
  const navigate = useNavigate();
  const { franchiseId } = useParams();
  const [score, setScore] = useState<number>(0);
  const [reviewText, setReviewText] = useState<string>('');
  const maxLength = 255;
  const [userInfo] = useAtom(userAtom);

  const handleTextChange = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
    if (e.target.value.length <= maxLength) {
      setReviewText(e.target.value);
    }
  };

  const handleRegistReview = () => {
    writeReview(
      userInfo.memberId,
      franchiseId + '',
      score,
      reviewText,
      location?.state.paymentId,
    );
    navigate('/review', { state: { refresh: true } });
    enqueueSnackbar('리뷰 등록에 성공했습니다.', { variant: 'success' });
  };

  return (
    <>
      <Helmet>
        <meta
          name="description"
          content="결식 아동이 결제한 음식점에 대해 리뷰를 작성할 수 있습니다."
        />
      </Helmet>
      <Layout className="px-8">
        <header className="mt-4 flex items-center justify-between">
          <BackButton />
          <PageTitle title="리뷰 관리" />
          <div className="w-8" />
        </header>
        <main>
          <section className="mt-4 border-b-2 text-center">
            <h1 className="text-xl font-bold">
              {location.state?.franchiseName}
            </h1>
            <p className="mt-4 pb-4 text-base text-DARKBASE">
              {location.state?.createdAt}
            </p>
          </section>
          <section className="mt-4 flex flex-col items-center gap-4 border-b-2 pb-8">
            <h3 className="text-lg">음식은 어떠셨나요?</h3>
            <p className="mb-2 text-sm text-DARKBASE">별점으로 평가해주세요.</p>
            <StarRating
              value={score}
              canEdit={true}
              isLarge={true}
              onChange={setScore}
            />
          </section>
          <section className="my-4 flex flex-col items-center">
            <h3 className="text-lg">어떤점이 좋았나요?</h3>
            <textarea
              className="mb-2 mt-4 w-full rounded-lg border border-gray-300 p-2 text-base focus:border-TERTIARY focus:outline-none"
              value={reviewText}
              onChange={handleTextChange}
              placeholder="여기에 리뷰를 작성해주세요..."
              rows={4}
              maxLength={maxLength}
            />
            <div className="mr-2 w-full text-right text-sm text-DARKBASE">
              {reviewText.length}/{maxLength}
            </div>
          </section>
          <Button
            label="리뷰 등록하기"
            onClick={handleRegistReview}
            disabled={score === 0}
          />
        </main>
      </Layout>
    </>
  );
};

export default ReviewWritePage;
