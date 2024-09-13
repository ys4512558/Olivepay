interface user {
  id: number;
  name: string;
  phoneNumber: string;
  nickName: string;
}

interface simpleFranchise {
  id: number;
  name: string;
}

interface unwriteReview {
  franchise: simpleFranchise;
  createdAt: string;
}

interface review {
  reviewId: number;
  franchise: simpleFranchise;
  stars: number;
  content: string;
}
