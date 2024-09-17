import { useState } from 'react';
import { useLocation, useParams } from 'react-router-dom';
import {
  Layout,
  BackButton,
  PageTitle,
  StarRating,
  Button,
} from '../component/common';
import { writeReview } from '../api/reviewApi';

const ReviewWritePage = () => {
  const location = useLocation();
  const { franchiseId } = useParams();
  const [score, setScore] = useState<number>(0);
  const [reviewText, setReviewText] = useState<string>('');
  const maxLength = 255;
  const memberId = 1;

  const handleTextChange = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
    if (e.target.value.length <= maxLength) {
      setReviewText(e.target.value);
    }
  };

  const handleRegistReview = () => {
    // writeReview(memberId, location.state.franchiseId, score, reviewText);
    console.log(memberId, franchiseId, score, reviewText);
  };

  return (
    <Layout className="px-8">
      <header className="mt-8 flex items-center justify-between">
        <BackButton />
        <PageTitle title="리뷰 관리" />
        <div className="w-8" />
      </header>
      <main>
        <section className="mt-12 border-b-2 text-center">
          <h1 className="text-3xl font-bold">{location.state.franchiseName}</h1>
          <p className="mt-4 pb-4 text-DARKBASE">{location.state.createdAt}</p>
        </section>
        <section className="mt-8 flex flex-col items-center gap-4 border-b-2 pb-8">
          <h3 className="text-xl">음식은 어떠셨나요?</h3>
          <p className="mb-2 text-DARKBASE">별점으로 평가해주세요.</p>
          <StarRating
            value={score}
            canEdit={true}
            isLarge={true}
            onChange={setScore}
          />
        </section>
        <section className="mb-4 mt-8 flex flex-col items-center">
          <h3 className="text-xl">어떤점이 좋았나요?</h3>
          <textarea
            className="mb-2 mt-4 w-full rounded-lg border border-gray-300 p-2 focus:border-TERTIARY focus:outline-none"
            value={reviewText}
            onChange={handleTextChange}
            placeholder="여기에 리뷰를 작성해주세요..."
            rows={8}
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
  );
};

export default ReviewWritePage;
