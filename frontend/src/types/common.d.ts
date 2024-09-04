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

type InputProps = {
  name: string;
  className?: string;
  type?: string;
  placeholder?: string;
  value?: string;
  checked?: boolean;
  maxLength?: number;
  autoComplete?: string;
  readOnly?: boolean;
  disabled?: boolean;
  onClick?: () => void;
  onChange?: (e: React.ChangeEvent<HTMLInputElement>) => void;
  onKeyDown?: (e: React.KeyboardEvent<HTMLInputElement>) => void;
};
