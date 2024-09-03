import { useAtom } from 'jotai';
import { userAtom } from '../atoms';

import { Button } from '../component/common';

const Main = () => {
  const [user, setUser] = useAtom(userAtom);
  return (
    <>
      <p className="m-2 w-1/2 bg-red-200 p-4 py-2 text-center text-4xl">
        설정 완료! by {user.name}
      </p>
      <button onClick={() => setUser({ name: '소희', isLoggedIn: true })}>
        바꿔!
      </button>
      <Button />
    </>
  );
};

export default Main;
