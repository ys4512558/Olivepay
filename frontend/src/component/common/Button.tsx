import clsx from 'clsx';

const tw = (strings: TemplateStringsArray): string => {
  return strings.join('');
};

const BUTTON_VARIANTS = {
  primary: tw`h-14 w-full rounded-3xl bg-PRIMARY text-white shadow-md disabled:bg-BASE`,
  secondary: tw`h-10 w-1/4 rounded-md bg-SECONDARY text-white`,
  text: tw`w-1/4 text-black hover:underline`,
};

const Button = ({
  className,
  variant = 'primary',
  label,
  onClick,
  disabled,
}: ButtonProps) => {
  return (
    <button
      className={clsx(
        'disabled:cursor-not-allowed',
        BUTTON_VARIANTS[variant],
        className,
      )}
      onClick={onClick}
      disabled={disabled}
    >
      {label}
    </button>
  );
};

export default Button;
