import { forwardRef } from 'react';
import clsx from 'clsx';

const Input = forwardRef<HTMLInputElement, InputProps>(
  (
    {
      name,
      className = 'w-full',
      type = 'text',
      placeholder = '',
      value,
      onChange,
      checked,
      autoComplete = 'on',
      maxLength,
      onKeyDown,
      readOnly,
      disabled,
      onClick,
      required,
      onBlur,
    },
    ref,
  ) => {
    return (
      <input
        name={name}
        className={clsx(
          'h-14 rounded-full px-4 text-black shadow-xl outline-none focus:ring-2 focus:ring-PRIMARY',
          className,
          disabled && 'bg-LIGHTBASE',
        )}
        type={type}
        placeholder={placeholder}
        value={value}
        onChange={onChange}
        onKeyDown={onKeyDown}
        checked={checked}
        maxLength={maxLength}
        autoComplete={autoComplete}
        readOnly={readOnly}
        disabled={disabled}
        onClick={onClick}
        ref={ref}
        required={required}
        onBlur={onBlur}
      />
    );
  },
);

Input.displayName = 'Input';

export default Input;
