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

interface review {
  userId: number;
  userName: string;
  stars: number;
  content: string;
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
  reviews: review[];
};
