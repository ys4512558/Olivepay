import { BackspaceIcon } from '@heroicons/react/24/solid';

const KeyPad: React.FC<KeyPadProps> = ({ variant, onKeyPress = () => {} }) => {
  return (
    <div className="mx-auto grid w-64 grid-cols-3 gap-x-24 gap-y-10 text-center font-bold">
      {[1, 2, 3, 4, 5, 6, 7, 8, 9].map((num) => (
        <button key={num} onClick={() => onKeyPress(num)} className="text-2xl">
          {num}
        </button>
      ))}

      {variant === 'password' ? (
        <>
          <div />
          <button onClick={() => onKeyPress(0)} className="text-2xl">
            0
          </button>
          <button onClick={() => onKeyPress('delete')} className="text-2xl">
            <BackspaceIcon className="mx-auto h-6 w-6" />
          </button>
        </>
      ) : (
        <>
          <button onClick={() => onKeyPress('00')} className="text-2xl">
            00
          </button>
          <button onClick={() => onKeyPress(0)} className="text-2xl">
            0
          </button>
          <button onClick={() => onKeyPress('delete')} className="text-2xl">
            <BackspaceIcon className="mx-auto h-6 w-6" />
          </button>
        </>
      )}
    </div>
  );
};

export default KeyPad;
