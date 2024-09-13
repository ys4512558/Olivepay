import Axios from '.';

const prefix = '/api/franchises/reviews';

// 리뷰 작성
export const writeReview = async (
  memberId: number,
  franchiseId: number,
  stars: number,
  content: string,
) => {
  const response = await Axios.post(`${prefix}`, {
    memberId: memberId,
    franchiseId: franchiseId,
    stars: stars,
    content: content,
  });
  return response.data;
};

// 리뷰 삭제
export const deleteReview = async (reviewId: number) => {
  const response = await Axios.delete(`${prefix}/${reviewId}`);
  return response.data;
};

// 작성 리뷰 조회
export const getReviews = async (memberId: number) => {
  const response = await Axios(`${prefix}/user/${memberId}`);
  return response.data;
};

// 작성 가능 리뷰 조회
export const getMissReviews = async (memberId: number) => {
  const response = await Axios(`${prefix}/miss/${memberId}`);
  return response.data;
};

// 특정 가맹점 리뷰 조회
export const getFranchiseReview = async (franchiseId: number) => {
  const response = await Axios(`${prefix}/franchise/${franchiseId}`);
  return response.data;
};
