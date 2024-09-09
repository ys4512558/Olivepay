import { useState, useRef } from 'react';

const BottomUp: React.FC<BottomUpProps> = ({ children }) => {
  const [isVisible, setIsVisible] = useState(true);
  const touchStartY = useRef<number | null>(null);
  const [isDragging, setIsDragging] = useState(false);
  const [offsetY, setOffsetY] = useState(0);

  const handleTouchStart = (e: React.TouchEvent) => {
    touchStartY.current = e.touches[0].clientY;
    setIsDragging(true);
  };

  const handleTouchMove = (e: React.TouchEvent) => {
    if (touchStartY.current === null) return;

    const currentY = e.touches[0].clientY;
    const diffY = currentY - touchStartY.current;

    if (diffY > 0 && diffY < 200) {
      setOffsetY(diffY);
    }
  };

  const handleTouchEnd = () => {
    if (offsetY > 100) {
      setIsVisible(false);
    } else {
      setOffsetY(0);
    }
    setIsDragging(false);
    touchStartY.current = null;
  };

  const handleReset = () => {
    setIsVisible(true);
    setOffsetY(0);
  };

  return (
    <>
      {isVisible && (
        <div
          className="relative flex w-full items-center justify-center"
          onTouchStart={handleTouchStart}
          onTouchMove={handleTouchMove}
          onTouchEnd={handleTouchEnd}
          style={{
            transform: `translateY(${offsetY}px)`,
            transition: isDragging ? 'none' : 'transform 0.3s ease',
          }}
        >
          <div className="fixed bottom-16 min-h-40 w-full max-w-md animate-slideUp rounded-tl-2xl rounded-tr-2xl border-x-2 border-t-2 p-8">
            {children}
          </div>
        </div>
      )}

      {/* 화면 하단에 남아 있는 핸들 */}
      {!isVisible && (
        <div
          className="relative flex w-full justify-center"
          onTouchStart={handleTouchStart}
          onTouchMove={handleTouchMove}
          onTouchEnd={handleTouchEnd}
          onClick={handleReset}
        >
          <div className="fixed bottom-16 h-6 w-full max-w-md rounded-tl-2xl rounded-tr-2xl border-x-2 border-t-2">
            {/* <div className="mx-8 mt-2 h-1 rounded-full bg-DARKBASE" /> */}
          </div>
        </div>
      )}
    </>
  );
};

export default BottomUp;
