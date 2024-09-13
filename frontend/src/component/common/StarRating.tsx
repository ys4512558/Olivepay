import ReactStars from 'react-rating-stars-component';
import { StarIcon } from '@heroicons/react/24/solid';

const StarRating: React.FC<StarRatingProps> = ({
  value = 0,
  canEdit = false,
  onChange = () => {},
}) => {
  return (
    <ReactStars
      count={5}
      value={value}
      edit={canEdit}
      onChange={onChange}
      emptyIcon={<StarIcon className="size-5 text-BASE" />}
      filledIcon={<StarIcon className="size-5 text-YELLOW" />}
    />
  );
};

export default StarRating;
