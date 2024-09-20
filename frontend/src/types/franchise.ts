export enum franchiseCategory {
  KOREAN = '한식',
  CHINESE = '중식',
  JAPANESE = '일식',
  WESTERN = '양식',
  GENERAL = '일반대중음식',
  FASTFOOD = '패스트푸드',
  BAKERY = '제과점',
  SUPERMARKET = '할인점/슈퍼마켓',
  OTHER = '기타',
}

export type franchise = {
  franchiseId: number;
  franchiseName: string;
  category: franchiseCategory;
  likes: number;
  isLiked?: boolean;
  address: string;
  coupon2: number;
  coupon4: number;
  reviews: number;
};

interface restaurant {
  franchiseId: number;
  franchiseName: string;
  category: string;
  likes: number;
  avgStars: number;
  coupons: number;
  latitude: number;
  longitude: number;
}

export type restaurants = restaurant[];

export interface FranchiseProps {
  franchiseName: string;
  category: franchiseCategory;
  className?: string;
}
