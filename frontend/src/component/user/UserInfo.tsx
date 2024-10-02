import clsx from 'clsx';

const UserInfo: React.FC<UserProps> = ({ user, className }) => {
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
        <p className="text-base text-BASE">{user.phoneNumber}</p>
      </div>
    </div>
  );
};

export default UserInfo;
