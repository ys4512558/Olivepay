import { useNavigate } from 'react-router-dom';
import { ChevronLeftIcon } from '@heroicons/react/24/solid';

const BackButton = () => {
  const navigate = useNavigate();
  return (
    <button className="rounded border-2 p-2" onClick={() => navigate(-1)}>
      <ChevronLeftIcon className="size-5" />
    </button>
  );
};

export default BackButton;
