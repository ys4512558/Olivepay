import { useMemo, useState, useEffect } from 'react';
import clsx from 'clsx';

const UserInfo: React.FC<UserProps> = ({ user, className }) => {
  const [imageLoaded, setImageLoaded] = useState(false);

  const formattedPhoneNumber = useMemo(() => {
    return `${user?.phoneNumber?.slice(0, 3)}-${user?.phoneNumber?.slice(3, 7)}-${user?.phoneNumber?.slice(-4)}`;
  }, [user?.phoneNumber]);

  useEffect(() => {
    setImageLoaded(false);
  }, [user?.memberId]);

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
      {!imageLoaded && (
        <div className="ml-4 mr-1 size-16 text-base">로딩중...</div>
      )}
      <img
        src={`https://api.multiavatar.com/${user.memberId}.png`}
        alt="user_avatar"
        className={clsx('ml-4 mr-1 size-16', { hidden: !imageLoaded })}
        onLoad={() => setImageLoaded(true)}
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
