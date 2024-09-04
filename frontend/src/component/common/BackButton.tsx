import { ChevronLeftIcon } from '@heroicons/react/24/solid';

const BackButton = () => {
  return (
    <button
      className="rounded border-2 p-2"
      onClick={() => window.history.back()}
    >
      <ChevronLeftIcon className="h-5 w-5" />
    </button>
  );
};

export default BackButton;
