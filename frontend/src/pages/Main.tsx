import { useEffect } from 'react';
import { useAtom } from 'jotai';
import { useQuery } from '@tanstack/react-query';
import { userAtom } from '../atoms';

import { getUsersInfo } from '../api/userApi';
import { Layout, Button } from '../component/common';

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
      <Button />
    </Layout>
  );
};

export default Main;
