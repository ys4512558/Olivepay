import { Button, Success } from '../common';
import { useNavigate } from 'react-router-dom';

const Donate5 = () => {
  const navigate = useNavigate();
  return (
    <>
      <section className="mb-8 mt-24 text-center">
        <Success />
        <h3 className="mt-20 text-xl font-semibold">후원이 완료되었습니다.</h3>
      </section>
      <section className="mx-8 mt-16">
        <Button label="확인" onClick={() => navigate('/donation-info')} />
      </section>
    </>
  );
};

export default Donate5;
