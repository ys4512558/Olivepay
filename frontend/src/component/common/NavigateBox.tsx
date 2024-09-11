import clsx from 'clsx';
import { useNavigate } from 'react-router-dom';

const NavigateBox: React.FC<NavigateBoxProps> = ({
  icon,
  text,
  path,
  className,
  bigger,
}) => {
  const navigate = useNavigate();
  return (
    <div
      onClick={() => navigate(path)}
      className={clsx(
        'flex items-center justify-center gap-4 rounded-xl border-2 p-2 shadow-md',
        className,
      )}
    >
      {icon}
      <span className={clsx(bigger && 'text-xl')}>{text}</span>
    </div>
  );
};

export default NavigateBox;
