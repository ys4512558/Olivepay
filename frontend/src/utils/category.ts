import { franchiseCategory } from '../types/franchise';

export const getFranchiseCategoryEmoji = (
  category: franchiseCategory,
): string => {
  switch (category) {
    case franchiseCategory.KOREAN:
      return '🍚';
    case franchiseCategory.CHINESE:
      return '🍜';
    case franchiseCategory.JAPANESE:
      return '🍣';
    case franchiseCategory.WESTERN:
      return '🍝';
    case franchiseCategory.GENERAL:
      return '🍲';
    case franchiseCategory.FASTFOOD:
      return '🍔';
    case franchiseCategory.BAKERY:
      return '🍞';
    case franchiseCategory.SUPERMARKET:
      return '🛒';
    case franchiseCategory.OTHER:
      return '🍙';
    default:
      return '🍴';
  }
};
