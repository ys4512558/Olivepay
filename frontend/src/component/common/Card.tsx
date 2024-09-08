import { useState } from 'react';
import clsx from 'clsx';
import StarRating from './StarRating';
import {
  StarIcon,
  MapPinIcon,
  HandThumbUpIcon,
  ChevronRightIcon,
  ChevronDownIcon,
  ChevronUpIcon,
} from '@heroicons/react/24/solid';

const tw = (strings: TemplateStringsArray): string => strings.join('');

const CARD_VARIANTS: Record<string, CardVariantStyles> = {
  restaurant: {
    container: tw`flex h-28 items-center justify-between rounded-xl border-2 p-4`,
    title: tw`text-2xl font-semibold`,
    category: tw`mt-2 text-xl text-DARKBASE`,
    score: tw`flex items-end gap-2`,
    like: tw`flex gap-2`,
  },
  payment: {
    container: tw`flex flex-col justify-between rounded-xl border-2 px-4 py-6`,
    header: tw`flex items-end justify-between`,
    title: tw`max-w-2/3 text-xl font-semibold`,
    spend: tw`ml-4 text-lg`,
    details: tw`flex`,
  },
  review: {
    container: tw`border-b-2 p-4`,
    title: tw`text-lg font-semibold text-DARKBASE`,
    content: tw`mt-2 min-h-12`,
  },
  donation: {
    container: tw`rounded-md border-2 p-4`,
    header: tw`flex justify-between`,
    title: tw`text-lg font-semibold`,
    date: tw`ml-4 text-DARKBASE`,
    location: tw`mt-2 flex gap-1 text-DARKBASE`,
    price: tw`mt-4 text-2xl font-bold`,
  },
};

const Card: React.FC<CardProps> = ({
  variant,
  title,
  category,
  score,
  like,
  price,
  spend,
  content,
  details,
  location,
  date,
  onClick,
}) => {
  const styles = CARD_VARIANTS[variant];
  const [isExpanded, setIsExpanded] = useState(false);
  const toggleExpand = () => setIsExpanded(!isExpanded);

  return (
    <article className={styles.container}>
      <div className={styles.header}>
        <div className="flex items-end gap-2">
          <h3 className={styles.title}>{title}</h3>
          {score && like && (
            <div className={styles.score}>
              <StarIcon className="size-6 text-YELLOW" />
              {score}
            </div>
          )}
          {score && content && <StarRating value={score} />}
          {like && (
            <p className={styles.like}>
              <HandThumbUpIcon className="size-6 text-PRIMARY" />
              {like}
            </p>
          )}
          {spend && <p className={styles.spend}>-{spend.toLocaleString()}원</p>}
        </div>
        {date && (
          <time className={styles.date} dateTime={date}>
            {date}
          </time>
        )}
        {spend && (
          <span onClick={toggleExpand}>
            {isExpanded ? (
              <ChevronUpIcon className="ml-4 size-5" />
            ) : (
              <ChevronDownIcon className="ml-4 size-5" />
            )}
          </span>
        )}
        {category && <h4 className={styles.category}>{category}</h4>}
      </div>
      {location && (
        <address className={styles.location}>
          <MapPinIcon className="size-5" />
          {location}
        </address>
      )}
      {like && <ChevronRightIcon className="size-8" onClick={onClick} />}
      {isExpanded && spend && (
        <hr className="my-4 border-dashed border-PRIMARY" />
      )}
      {isExpanded && details && (
        <div>
          {details.map((el: payment) => (
            <div className={styles.details} key={el.name}>
              <p className="w-32">{el.name}</p>
              <p>{el.amount.toLocaleString()}원</p>
            </div>
          ))}
        </div>
      )}
      {content && (
        <div
          className={clsx(
            styles.content,
            isExpanded ? 'h-auto' : 'line-clamp-2',
          )}
          onClick={toggleExpand}
        >
          {content}
        </div>
      )}
      {price && (
        <strong className={styles.price}>{price.toLocaleString()}원</strong>
      )}
    </article>
  );
};

export default Card;
