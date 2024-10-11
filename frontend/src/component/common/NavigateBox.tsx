import { useCallback } from 'react';
import clsx from 'clsx';
import { useNavigate } from 'react-router-dom';

const NavigateBox: React.FC<NavigateBoxProps> = ({
  icon,
  text,
  path,
  className,
  bigger,
  onClick,
}) => {
  const navigate = useNavigate();

  const handleClick = useCallback(() => {
    if (onClick) {
      onClick();
    } else {
      navigate(path);
    }
  }, [onClick, navigate, path]);

  const textClass = clsx(bigger ? 'text-md' : 'text-base');
  return (
    <div
      onClick={handleClick}
      className={clsx(
        'flex items-center justify-center gap-3 rounded-xl border-2 p-2 shadow-md',
        className,
      )}
    >
      {icon}
      <span className={textClass}>{text}</span>
    </div>
  );
};

export default NavigateBox;
