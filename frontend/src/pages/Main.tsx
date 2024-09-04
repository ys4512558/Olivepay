import { useEffect } from 'react';
import { useAtom } from 'jotai';
import { useQuery } from '@tanstack/react-query';
import { userAtom } from '../atoms';

import { getUsersInfo } from '../api/userApi';
import { Layout, Button, BackButton, Input } from '../component/common';

const Main = () => {
  const [users, setUsers] = useAtom(userAtom);
  const { data, error, isLoading } = useQuery({
    queryKey: ['users'],
    queryFn: getUsersInfo,
  });

  useEffect(() => {
    if (data) {
      setUsers(data);
    }
  }, [data, setUsers]);

  if (isLoading) return <div>로딩 중...</div>;
  if (error) return <div>에러 발생: {error.message}</div>;
  return (
    <Layout>
      {users.map((user) => (
        <div key={user.id}>{user.name}</div>
      ))}
      <Button variant="primary" label="첫번째" />
      <Button variant="secondary" label="두번째" />
      <Button variant="text" label="텍스트" />
      <BackButton />
      <br />
      <Input name="first" />
      <Input name="second" className="w-1/2" />
    </Layout>
  );
};

export default Main;
