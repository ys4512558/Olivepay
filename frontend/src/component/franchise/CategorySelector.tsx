import clsx from 'clsx';
import { franchiseCategory } from '../../types/franchise';
import { getFranchiseCategoryEmoji } from '../../utils/category';

interface CategorySelectorProps {
  selectedCategory: string | null;
  handleCategoryClick: (category: string) => void;
}

const CategorySelector: React.FC<CategorySelectorProps> = ({
  selectedCategory,
  handleCategoryClick,
}) => {
  return (
    <div className="absolute left-2 top-4 z-10 flex w-full space-x-2 overflow-x-scroll pr-4 scrollbar-hide">
      {Object.values(franchiseCategory).map((category) => (
        <button
          key={category}
          onClick={() => handleCategoryClick(category)}
          className={clsx(
            'w-auto text-nowrap rounded-full border-2 px-4 py-2',
            selectedCategory === category
              ? 'bg-PRIMARY text-white'
              : 'bg-white text-black',
          )}
        >
          {getFranchiseCategoryEmoji(category)}
          <span className="ml-1">{category}</span>
        </button>
      ))}
    </div>
  );
};

export default CategorySelector;
