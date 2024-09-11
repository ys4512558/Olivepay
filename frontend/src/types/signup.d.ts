// Define the possible values for activeField
type ActiveField = 'password' | 'confirmPassword' | null;

interface UserSignUpProps {
  setStep: (step: number) => void;
  handleFormDataChange: (field: string, value: string) => void;
  formData: {
    name: string;
    nickname: string;
    phoneNumber: string;
    userPw: string;
    birthdate: string;
    pin: string;
  };
  handleSubmit?: () => void;
  handleKeyPress?: (value: string | number) => void; // Add handleKeyPress function
  activeField?: ActiveField; // Optionally define activeField if needed in props
  onClick?: () => void;
}
