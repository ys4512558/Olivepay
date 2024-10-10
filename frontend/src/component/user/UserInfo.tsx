import { useMemo } from 'react';
import clsx from 'clsx';

const UserInfo: React.FC<UserProps> = ({ user, className }) => {
  const formattedPhoneNumber = useMemo(() => {
    return `${user?.phoneNumber?.slice(0, 3)}-${user?.phoneNumber?.slice(3, 7)}-${user?.phoneNumber?.slice(-4)}`;
  }, [user?.phoneNumber]);

  if (!user) {
    return <div>Loading...</div>;
  }

  return (
    <div
      className={clsx(
        'flex items-center gap-2 rounded-xl border-2 shadow-md',
        className,
      )}
    >
      <img
        src="https://api.dicebear.com/9.x/lorelei/svg"
        alt="user_avatar"
        className="size-24"
      />
      <div className="flex flex-col gap-1">
        <p className="text-base">반갑습니다</p>
        <p className="text-xl">{user.nickname}님</p>
        <p className="text-base text-BASE">{formattedPhoneNumber}</p>
      </div>
    </div>
  );
};

export default UserInfo;
