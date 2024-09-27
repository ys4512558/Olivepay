import { useState } from 'react';
import { useAtom } from 'jotai';
import { useQuery } from '@tanstack/react-query';
import {
  Layout,
  BackButton,
  PageTitle,
  Loader,
  EmptyData,
} from '../component/common';
import { bookmarkedFranchiseAtom } from '../atoms/userAtom';
import { HeartIcon as HeartSolidIcon } from '@heroicons/react/24/solid';
import {
  HeartIcon as HeartOutlineIcon,
  ChevronRightIcon,
} from '@heroicons/react/24/outline';
import { getFavoriteFranchises } from '../api/franchiseApi';

const BookmarkPage = () => {
  const [franchises, setFranchises] = useAtom(bookmarkedFranchiseAtom);
  const [favorites, setFavorites] = useState(
    franchises.map((franchise) => ({
      id: franchise.franchise.id,
      isFavorite: true,
    })),
  );

  const { data, error, isLoading, isSuccess } = useQuery({
    queryKey: ['favorite'],
    queryFn: getFavoriteFranchises,
  });

  if (isSuccess && data) {
    setFranchises(data);
  }

  if (isLoading) return <Loader />;

  if (error) return <div>찜 목록 로딩 실패</div>;

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
      <header className="mx-8 mt-4 flex items-center justify-between">
        <BackButton />
        <PageTitle title="찜한 식당" />
        <div className="w-8" />
      </header>
      <main className="mt-4 h-[80dvh]">
        <section className="flex flex-col gap-4 overflow-y-scroll scrollbar-hide">
          {franchises.length === 0 && (
            <EmptyData label="찜한 식당이 없습니다." />
          )}
          {franchises.map((franchise) => (
            <div
              className="mx-8 flex items-center justify-between rounded-xl border-2 bg-white p-4 shadow-md"
              key={franchise.likeId}
            >
              <div className="flex items-center">
                <button
                  onClick={() => handleHeartClick(franchise.franchise.id)}
                >
                  {favorites.find((f) => f.id === franchise.franchise.id)
                    ?.isFavorite ? (
                    <HeartSolidIcon className="size-8 text-RED" />
                  ) : (
                    <HeartOutlineIcon className="size-8 text-RED" />
                  )}
                </button>
                <div className="ml-4">
                  <div className="flex items-center gap-2">
                    <h3 className="text-lg font-semibold">
                      {franchise.franchise.name}
                    </h3>
                    <span className="text-base text-DARKBASE">
                      {franchise.franchise.category}
                    </span>
                  </div>
                  <div className="text-sm">{franchise.franchise.address}</div>
                </div>
              </div>
              <ChevronRightIcon className="size-6" />
            </div>
          ))}
        </section>
      </main>
    </Layout>
  );
};

export default BookmarkPage;
