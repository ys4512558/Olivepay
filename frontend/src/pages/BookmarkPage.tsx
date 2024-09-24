import { useState } from 'react';
import { useAtom } from 'jotai';
import { Layout, BackButton, PageTitle } from '../component/common';
import { bookmarkedFranchiseAtom } from '../atoms/userAtom';
import { HeartIcon as HeartSolidIcon } from '@heroicons/react/24/solid';
import {
  HeartIcon as HeartOutlineIcon,
  ChevronRightIcon,
} from '@heroicons/react/24/outline';

const BookmarkPage = () => {
  const [franchises] = useAtom(bookmarkedFranchiseAtom);
  const [favorites, setFavorites] = useState(
    franchises.map((franchise) => ({
      id: franchise.franchise.id,
      isFavorite: true,
    })),
  );

  // 하트를 클릭했을 때 호출되는 함수
  const handleHeartClick = async (franchiseId: number) => {
    const updatedFavorites = favorites.map((favorite) =>
      favorite.id === franchiseId
        ? { ...favorite, isFavorite: !favorite.isFavorite }
        : favorite,
    );
    setFavorites(updatedFavorites);
  };
  return (
    <Layout>
      <header className="mx-8 mt-12 flex items-center justify-between">
        <BackButton />
        <PageTitle title="찜한 식당" />
        <div className="w-8" />
      </header>
      <main className="mt-8 h-[80dvh] bg-LIGHTBASE">
        <section className="flex flex-col gap-4 overflow-y-scroll py-8 scrollbar-hide">
          {franchises.map((franchise) => (
            <div className="mx-8 flex items-center justify-between rounded-xl border-2 bg-white p-4 shadow-md">
              <div className="flex items-center">
                <button
                  onClick={() => handleHeartClick(franchise.franchise.id)}
                >
                  {favorites.find((f) => f.id === franchise.franchise.id)
                    ?.isFavorite ? (
                    <HeartSolidIcon className="size-10 text-RED" />
                  ) : (
                    <HeartOutlineIcon className="size-10 text-RED" />
                  )}
                </button>
                <div className="ml-4">
                  <div className="flex items-center gap-2">
                    <h3 className="text-lg font-semibold">
                      {franchise.franchise.name}
                    </h3>
                    <span className="text-DARKBASE">
                      {franchise.franchise.category}
                    </span>
                  </div>
                  <div className="text-sm">{franchise.franchise.address}</div>
                </div>
              </div>
              <ChevronRightIcon className="size-8" />
            </div>
          ))}
        </section>
      </main>
    </Layout>
  );
};

export default BookmarkPage;
