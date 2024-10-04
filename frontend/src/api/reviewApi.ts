import Axios from '.';

const prefix = '/franchises/reviews';

// 리뷰 작성
export const writeReview = async (
  memberId: number,
  franchiseId: string,
  stars: number,
  content: string,
) => {
  const response = await Axios.post(`${prefix}/user`, {
    memberId: memberId,
    franchiseId: +franchiseId,
    stars: stars,
    content: content,
  });
  return response.data;
};

// 리뷰 삭제
export const deleteReview = async (reviewId: number) => {
  const response = await Axios.delete(`${prefix}/user/${reviewId}`);
  return response.data;
};

// 작성한 리뷰 조회
export const getReviews = async (index?: number) => {
  const url = index ? `${prefix}/user?index=${index}` : `${prefix}/user`;
  const response = await Axios(url);
  return response.data.data;
};

// 작성 가능 리뷰 조회
export const getMissReviews = async () => {
  const response = await Axios(`${prefix}/available`);
  return response.data.data;
};

// 특정 가맹점 리뷰 조회
export const getFranchiseReview = async (
  franchiseId: number,
  index?: number,
) => {
  const url = index
    ? `${prefix}/${franchiseId}?index=${index}`
    : `${prefix}/${franchiseId}`;
  const response = await Axios(url);
  return response.data.data;
};
