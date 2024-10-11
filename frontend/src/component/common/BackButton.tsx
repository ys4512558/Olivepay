import { useNavigate } from 'react-router-dom';
import { ChevronLeftIcon } from '@heroicons/react/24/solid';
import { useCallback } from 'react';

interface BackButtonProps {
  onClick?: () => void;
}

const BackButton: React.FC<BackButtonProps> = ({ onClick }) => {
  const navigate = useNavigate();

  const handleClick = useCallback(() => {
    if (onClick) {
      onClick();
    } else {
      navigate(-1);
    }
  }, [onClick, navigate]);

  return (
    <button className="rounded border-2 p-1" onClick={handleClick}>
      <ChevronLeftIcon className="size-5" />
    </button>
  );
};

export default BackButton;
