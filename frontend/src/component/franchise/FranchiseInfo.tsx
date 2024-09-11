import { franchiseCategory } from '../../types/franchise';
import { getFranchiseCategoryEmoji } from '../../utils/category';

interface FranchiseProps {
  franchiseName: string;
  category: franchiseCategory;
  likes: number;
  address: string;
  reviewCount: number;
}

const FranchiseInfo: React.FC<FranchiseProps> = ({
  franchiseName,
  category,
  likes,
  address,
  reviewCount,
}) => {
  return (
    <div>
      <p>반갑습니다 {getFranchiseCategoryEmoji(category)}</p>
      <p>{franchiseName} 사장님</p>
      <p>{likes}명이 찜했어요</p>
      <p>{address}</p>
      <p>{reviewCount}개 리뷰가 있어요</p>
    </div>
  );
};

export default FranchiseInfo;
