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

  const handleClick = () => {
    if (onClick) {
      onClick();
    } else {
      navigate(path);
    }
  };
  return (
    <div
      onClick={handleClick}
      className={clsx(
        'flex items-center justify-center gap-3 rounded-xl border-2 p-2 shadow-md',
        className,
      )}
    >
      {icon}
      <span className={clsx(bigger ? 'text-md' : 'text-base')}>{text}</span>
    </div>
  );
};

export default NavigateBox;
