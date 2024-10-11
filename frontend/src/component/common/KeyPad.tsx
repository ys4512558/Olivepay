import { useState } from 'react';
import clsx from 'clsx';
import { BackspaceIcon } from '@heroicons/react/24/solid';

const KeyPad: React.FC<KeyPadProps> = ({ variant, onKeyPress = () => {} }) => {
  const [pressedKey, setPressedKey] = useState<number | string | null>(null);
  const handleKeyPress = (key: number | string) => {
    setPressedKey(key);
    onKeyPress(key);

    setTimeout(() => {
      setPressedKey(null);
    }, 100);
  };
  return (
    <div className="mx-auto grid grid-cols-3 gap-x-14 text-center font-bold">
      {[1, 2, 3, 4, 5, 6, 7, 8, 9].map((num) => (
        <button
          key={num}
          onClick={() => handleKeyPress(num)}
          className={clsx(
            'rounded-full p-4 text-xl',
            pressedKey === num ? 'bg-PRIMARY text-white' : '',
          )}
        >
          {num}
        </button>
      ))}

      {variant === 'password' ? (
        <>
          <div />
          <button
            onClick={() => handleKeyPress(0)}
            className={clsx(
              'rounded-full py-4 text-xl',
              pressedKey === 0 ? 'bg-PRIMARY text-white' : '',
            )}
          >
            0
          </button>
          <button
            onClick={() => handleKeyPress('delete')}
            className={clsx(
              'rounded-full py-4 text-xl',
              pressedKey === 'delete' ? 'bg-PRIMARY text-white' : '',
            )}
          >
            <BackspaceIcon className="mx-auto h-6 w-6" />
          </button>
        </>
      ) : (
        <>
          <button
            onClick={() => handleKeyPress('00')}
            className={clsx(
              'rounded-full py-4 text-xl',
              pressedKey === '00' ? 'bg-PRIMARY text-white' : '',
            )}
          >
            00
          </button>
          <button
            onClick={() => handleKeyPress(0)}
            className={clsx(
              'rounded-full py-4 text-xl',
              pressedKey === 0 ? 'bg-PRIMARY text-white' : '',
            )}
          >
            0
          </button>
          <button
            onClick={() => handleKeyPress('delete')}
            className={clsx(
              'rounded-full py-4 text-2xl',
              pressedKey === 'delete' ? 'bg-PRIMARY text-white' : '',
            )}
          >
            <BackspaceIcon className="mx-auto h-6 w-6" />
          </button>
        </>
      )}
    </div>
  );
};

export default KeyPad;
