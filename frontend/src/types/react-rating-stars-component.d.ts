declare module 'react-rating-stars-component' {
  interface ReactStarsProps {
    count?: number;
    value?: number;
    edit?: boolean;
    onChange?: (newValue: number) => void;
    emptyIcon?: React.ReactNode;
    filledIcon?: React.ReactNode;
  }

  const ReactStars: React.FC<ReactStarsProps>;
  export default ReactStars;
}
