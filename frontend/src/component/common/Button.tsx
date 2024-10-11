import { useMemo } from 'react';
import clsx from 'clsx';

const tw = (strings: TemplateStringsArray): string => {
  return strings.join('');
};

const BUTTON_VARIANTS = {
  primary: tw`h-12 w-full rounded-3xl bg-PRIMARY text-base text-white shadow-md disabled:bg-BASE`,
  secondary: tw`h-10 w-1/4 rounded-md bg-SECONDARY text-base text-white`,
  text: tw`w-1/4 text-base text-black hover:underline`,
};

const Button = ({
  className,
  variant = 'primary',
  label,
  onClick,
  disabled,
  type = 'submit',
}: ButtonProps) => {
  const computedClassName = useMemo(() => {
    return clsx(
      'disabled:cursor-not-allowed',
      className,
      BUTTON_VARIANTS[variant],
    );
  }, [className, variant]);
  return (
    <button
      className={computedClassName}
      onClick={onClick}
      disabled={disabled}
      type={type}
    >
      {label}
    </button>
  );
};

export default Button;
