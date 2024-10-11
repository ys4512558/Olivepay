import { useEffect } from 'react';

const Success = () => {
  useEffect(() => {
    const confettiPieces = document.querySelectorAll(
      '.confetti-piece',
    ) as NodeListOf<HTMLElement>;

    confettiPieces.forEach((piece) => {
      const angle = Math.random() * Math.PI * 2;
      const horizontalBias = Math.cos(angle) * (80 + Math.random() * 120);
      const verticalBias = Math.sin(angle) * (70 + Math.random() * 60);
      const rotation = Math.random() * 360;

      piece.style.setProperty('--x', `${horizontalBias}px`);
      piece.style.setProperty('--y', `${verticalBias}px`);
      piece.style.setProperty('--rotate', `${rotation}deg`);
    });
  }, []);
  return (
    <div className="relative inset-0 flex items-center justify-center">
      <div
        className="animate-appear flex h-24 w-24 items-center justify-center bg-PRIMARY"
        style={{
          clipPath:
            'polygon(50% 0%, 85% 15%, 100% 50%, 85% 85%, 50% 100%, 15% 85%, 0% 50%, 15% 15%)',
        }}
      >
        <svg
          width="70"
          height="70"
          viewBox="0 0 100 100"
          xmlns="http://www.w3.org/2000/svg"
        >
          <path
            d="M20 50 L40 70 L80 30"
            fill="none"
            stroke="#fff"
            strokeWidth="9"
            strokeLinecap="round"
            strokeDasharray="100"
            strokeDashoffset="100"
          >
            <animate
              attributeName="stroke-dashoffset"
              from="100"
              to="0"
              dur="0.5s"
              fill="freeze"
            />
          </path>
        </svg>
      </div>
      {[...Array(39)].map((_, index) => (
        <div
          key={index}
          className="confetti-piece animate-explode absolute rounded-sm bg-gradient-to-br from-PRIMARY to-SECONDARY"
          style={{
            width: `${Math.random() * 4 + 2}px`,
            height: `${Math.random() * 4 + 2}px`,
          }}
        ></div>
      ))}
    </div>
  );
};

export default Success;
