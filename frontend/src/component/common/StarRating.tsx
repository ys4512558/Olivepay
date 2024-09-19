import ReactStars from 'react-rating-stars-component';
import clsx from 'clsx';
import { StarIcon } from '@heroicons/react/24/solid';

const StarRating: React.FC<StarRatingProps> = ({
  value = 0,
  canEdit = false,
  onChange = () => {},
  isLarge = false,
}) => {
  return (
    <ReactStars
      count={5}
      value={value}
      edit={canEdit}
      onChange={onChange}
      emptyIcon={
        <StarIcon
          className={clsx('text-BASE', isLarge ? 'size-12' : 'size-5')}
        />
      }
      filledIcon={
        <StarIcon
          className={clsx('text-YELLOW', isLarge ? 'size-12' : 'size-5')}
        />
      }
    />
  );
};

export default StarRating;
