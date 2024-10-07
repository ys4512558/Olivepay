import { NavLink } from 'react-router-dom';

import {
  HomeIcon,
  MapIcon,
  ViewfinderCircleIcon,
  UserIcon,
  CreditCardIcon,
} from '@heroicons/react/24/solid';

const NAV_ITEMS = [
  { path: '/', icon: HomeIcon },
  { path: '/map', icon: MapIcon },
  { path: '/pay', icon: ViewfinderCircleIcon },
  { path: '/home', icon: UserIcon },
  { path: '/history', icon: CreditCardIcon },
] as const;

const BottomTab = () => {
  return (
    <nav className="fixed bottom-0 z-30 flex h-12 w-full max-w-md animate-slideUp items-center justify-around border-t-2 bg-white px-8 py-3">
      {NAV_ITEMS.map((item) => (
        <NavLink
          key={item.path}
          to={item.path}
          className={({ isActive }) =>
            `flex flex-col items-center ${isActive ? 'text-PRIMARY' : 'text-DARKBASE'}`
          }
        >
          {item.path === '/pay' ? (
            <>
              <div
                className="absolute -top-[38px] z-10 h-20 w-20 rounded-full border-x-2 border-t-2 bg-white"
                style={{ clipPath: 'inset(0 0 52.5% 0)' }}
              ></div>
              <div className="absolute -top-6 z-30 flex h-14 w-14 items-center justify-center rounded-full bg-PRIMARY">
                <item.icon className="size-6 text-white" />
              </div>
            </>
          ) : (
            <item.icon className="size-5" />
          )}
        </NavLink>
      ))}
    </nav>
  );
};

export default BottomTab;
