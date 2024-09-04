declare const BUTTON_VARIANTS: {
  primary: string;
  secondary: string;
  text: string;
};

type LayoutProps = {
  children: React.ReactNode;
  className?: string;
};

type ButtonProps = {
  className?: string;
  variant?: keyof typeof BUTTON_VARIANTS;
  label: string;
  disabled?: boolean;
  onClick?: () => void;
};

type BackButtonProps = {
  className?: string;
};
