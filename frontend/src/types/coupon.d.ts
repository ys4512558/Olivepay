interface coupon {
  coupon2: number;
  coupon4: number;
}

type couponList = {
  franchiseId: number;
  franchiseName?: string;
  coupon2: number;
  coupon4: number;
}[];

interface myCoupon {
  franchiseId: number;
  franchiseName: string;
  couponUnit: string;
  message: string;
}
