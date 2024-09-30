import { useDaumPostcodePopup } from 'react-daum-postcode';
import { Button } from '../common';
interface AddressData {
  address: string;
  addressType: 'R' | 'J';
  bname: string;
  buildingName: string;
}

interface PostCodeSearchProps {
  onAddressSelect: (address: string) => void;
}

const PostCodeSearch: React.FC<PostCodeSearchProps> = ({ onAddressSelect }) => {
  const scriptUrl =
    'https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js';
  const open = useDaumPostcodePopup(scriptUrl);

  const handleComplete = (data: AddressData) => {
    let fullAddress = data.address;
    let extraAddress = '';

    if (data.addressType === 'R') {
      if (data.bname !== '') {
        extraAddress += data.bname;
      }
      if (data.buildingName !== '') {
        extraAddress +=
          extraAddress !== '' ? `, ${data.buildingName}` : data.buildingName;
      }
      fullAddress += extraAddress !== '' ? ` (${extraAddress})` : '';
    }

    onAddressSelect(fullAddress);
  };

  const handleClick = () => {
    open({ onComplete: handleComplete });
  };

  return (
    <>
      <Button variant="secondary" label="검색하기" onClick={handleClick} />
    </>
  );
};

export default PostCodeSearch;
