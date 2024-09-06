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
  { path: '/order', icon: ViewfinderCircleIcon },
  { path: '/profile', icon: UserIcon },
  { path: '/card', icon: CreditCardIcon },
] as const;

export const BottomTab = () => {
  return (
    <nav className="animate-slideUp fixed bottom-0 z-30 flex h-16 w-full max-w-md items-center justify-around border-x-2 border-t-2 bg-white px-8 py-3">
      {NAV_ITEMS.map((item) => (
        <NavLink
          key={item.path}
          to={item.path}
          className={({ isActive }) =>
            `flex flex-col items-center ${isActive ? 'text-PRIMARY' : 'text-DARKBASE'}`
          }
        >
          {item.path === '/order' ? (
            <>
              {/* 중앙 흰색 원 */}
              <div
                className="absolute -top-11 z-40 h-20 w-20 rounded-full border-x-2 border-b-2 bg-white"
                style={{ clipPath: 'inset(52.5% 0 0 0)' }}
              ></div>
              {/* 중앙 초록색 원 */}
              <div className="absolute -top-8 z-50 flex h-14 w-14 items-center justify-center rounded-full bg-PRIMARY">
                <item.icon className="h-7 w-7 text-white" />
              </div>
            </>
          ) : (
            <item.icon className="size-6" />
          )}
        </NavLink>
      ))}
    </nav>
  );
};
