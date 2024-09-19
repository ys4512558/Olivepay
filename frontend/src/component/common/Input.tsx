import { useState, forwardRef } from 'react';
import clsx from 'clsx';

import { EyeIcon, EyeSlashIcon } from '@heroicons/react/24/solid';

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
      minLength,
      onKeyDown,
      readOnly,
      disabled,
      onClick,
      required,
      onBlur,
      container,
    },
    ref,
  ) => {
    const [showPassword, setShowPassword] = useState(false);

    const handleTogglePassword = () => {
      setShowPassword(!showPassword);
    };

    const isPasswordType = type === 'password';
    return (
      <div className={clsx('relative w-full', container)}>
        <input
          name={name}
          className={clsx(
            'h-14 w-full rounded-full px-4 text-black shadow-xl outline-none focus:ring-2 focus:ring-PRIMARY',
            className,
            disabled && 'bg-LIGHTBASE',
          )}
          type={isPasswordType && showPassword ? 'text' : type}
          placeholder={placeholder}
          value={value}
          onChange={onChange}
          onKeyDown={onKeyDown}
          checked={checked}
          maxLength={maxLength}
          minLength={minLength}
          autoComplete={autoComplete}
          readOnly={readOnly}
          disabled={disabled}
          onClick={onClick}
          ref={ref}
          required={required}
          onBlur={onBlur}
        />
        {isPasswordType && (
          <button
            type="button"
            className="absolute inset-y-0 right-8"
            onClick={handleTogglePassword}
          >
            {showPassword ? (
              <EyeSlashIcon className="size-6 text-DARKBASE" />
            ) : (
              <EyeIcon className="size-6 text-DARKBASE" />
            )}
          </button>
        )}
      </div>
    );
  },
);

Input.displayName = 'Input';

export default Input;
