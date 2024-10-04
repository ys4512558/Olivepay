type ActiveField = 'password' | 'confirmPassword' | null;

interface UserSignUpProps {
  setStep: (step: number) => void;
  handleFormDataChange: (
    field: string,
    value: string,
    formType: 'formData1' | 'formData2',
  ) => void;

  formData1: {
    name: string;
    nickname: string;
    phoneNumber: string;
    userPw: string;
    birthdate: string;
    pin: string;
  };

  formData2: {
    name: string;
    phoneNumber: string;
    userPw: string;
    telephoneNumber: string;
    franchiseName: string;
    registrationNumber: string;
    address: string;
    category: string;
    rrnPrefix: string;
    rrnCheckDigit: string;
    longitude: number;
    latitude: number;
  };

  handleSubmit?: () => void;
  handleKeyPress?: (value: string | number) => void;
  activeField?: ActiveField;
  onClick?: () => void;
  signupType: string;
}

interface CardScanProps {
  setStep?: (step: number) => void;
  handleFormDataChange?: (field: string, value: string) => void;
}

type TermsChecked = {
  term1: boolean;
  term2: boolean;
  term3: boolean;
};

interface CheckPinCodeProps {
  handlePaySuccess: (pinCode: string) => void;
}
