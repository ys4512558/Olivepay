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
  handleKeyPress?: (value: string | number) => void;
  activeField?: ActiveField;
  onClick?: () => void;
}

interface CardScanProps {
  setStep?: (step: number) => void;
  handleFormDataChange?: (field: string, value: string) => void; // 이 부분도 필요에 따라 추가
}

type TermsChecked = {
  term1: boolean;
  term2: boolean;
  term3: boolean;
};

interface CheckPinCodeProps {
  handlePaySuccess: () => void;
}
