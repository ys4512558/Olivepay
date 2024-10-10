import { useState, useRef, useCallback, useMemo } from 'react';
import clsx from 'clsx';

const BottomUp: React.FC<BottomUpProps> = ({
  children,
  className,
  isVisible: propIsVisible,
  setIsVisible: propSetIsVisible,
  fromMap,
}) => {
  const [internalIsVisible, setInternalIsVisible] = useState(true);
  const isVisible =
    propIsVisible !== undefined ? propIsVisible : internalIsVisible;
  const setIsVisible =
    propSetIsVisible !== undefined ? propSetIsVisible : setInternalIsVisible;
  const touchStartY = useRef<number | null>(null);
  const [isDragging, setIsDragging] = useState(false);
  const [offsetY, setOffsetY] = useState(0);
  const [isScrolling, setIsScrolling] = useState(false);

  const handleTouchStart = useCallback((e: React.TouchEvent) => {
    touchStartY.current = e.touches[0].clientY;
    setIsDragging(true);
  }, []);

  const handleTouchMove = useCallback(
    (e: React.TouchEvent) => {
      if (isScrolling || touchStartY.current === null) return;

      const currentY = e.touches[0].clientY;
      const diffY = currentY - touchStartY.current;

      if (diffY >= 15) {
        setOffsetY(1);
      } else {
        setOffsetY(0);
      }
    },
    [isScrolling],
  );

  const handleTouchEnd = useCallback(() => {
    if (isScrolling) return;
    if (offsetY > 0) {
      setIsVisible(false);
    } else {
      setOffsetY(0);
    }
    setIsDragging(false);
    touchStartY.current = null;
  }, [isScrolling, offsetY, setIsVisible]);

  const handleReset = useCallback(() => {
    setIsVisible(true);
    setOffsetY(0);
  }, [setIsVisible]);

  const handleScroll = useCallback((e: React.UIEvent<HTMLDivElement>) => {
    const target = e.target as HTMLDivElement;
    const isAtTop = target.scrollTop === 0;
    const isAtBottom =
      target.scrollTop + target.clientHeight === target.scrollHeight;

    setIsScrolling(!isAtTop && !isAtBottom);
  }, []);

  const containerClass = useMemo(() => {
    return clsx(
      className,
      'fixed bottom-0 min-h-40 w-full max-w-md animate-slideUp rounded-tl-2xl rounded-tr-2xl border-x-2 border-t-2 bg-white',
    );
  }, [className]);

  return (
    <>
      {isVisible && (
        <div
          className="relative z-20 flex w-full items-center justify-center"
          onTouchStart={handleTouchStart}
          onTouchMove={handleTouchMove}
          onTouchEnd={handleTouchEnd}
          style={{
            transform: `translateY(${offsetY}px)`,
            transition: isDragging ? 'none' : 'transform 0.3s ease',
          }}
        >
          <div className={containerClass} onScroll={handleScroll}>
            <div className="sticky top-0 z-10 flex h-8 w-full justify-center rounded-full bg-white">
              <div className="mt-2 h-1 w-12 rounded-md bg-BASE" />
            </div>
            <div className="px-8">{children}</div>
          </div>
        </div>
      )}

      {!isVisible && (
        <div
          className="relative flex w-full justify-center"
          onTouchStart={handleTouchStart}
          onTouchMove={handleTouchMove}
          onTouchEnd={handleTouchEnd}
          onClick={handleReset}
        >
          <div
            className={clsx(
              'fixed bottom-0 z-20 flex w-full max-w-md justify-center rounded-tl-2xl rounded-tr-2xl border-x-2 border-t-2 bg-white pt-2',
              fromMap ? 'h-14' : 'h-28',
            )}
          >
            <div className="h-1 w-12 rounded-full bg-BASE" />
          </div>
        </div>
      )}
    </>
  );
};

export default BottomUp;
