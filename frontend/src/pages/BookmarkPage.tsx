import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
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
import { getFavoriteFranchises, toggleLike } from '../api/franchiseApi';
import { franchiseCategory } from '../types/franchise';

const BookmarkPage = () => {
  const navigate = useNavigate();
  const [franchises, setFranchises] = useAtom(bookmarkedFranchiseAtom);
  const [favorites, setFavorites] = useState(
    franchises.map((franchise) => ({
      id: franchise.franchiseId,
      isFavorite: true,
    })),
  );

  const { data, error, isLoading, isSuccess } = useQuery({
    queryKey: ['favorite'],
    queryFn: getFavoriteFranchises,
  });

  useEffect(() => {
    if (isSuccess && data) {
      setFranchises(data);
      setFavorites(
        franchises.map((franchise) => ({
          id: franchise.franchiseId,
          isFavorite: true,
        })),
      );
    }
  }, [isSuccess, data, franchises, setFranchises]);

  if (isLoading) return <Loader />;

  if (error) return <div>찜 목록 로딩 실패</div>;

  const handleHeartClick = async (franchiseId: number) => {
    const updatedFavorites = favorites.map((favorite) =>
      favorite.id === franchiseId
        ? { ...favorite, isFavorite: !favorite.isFavorite }
        : favorite,
    );
    toggleLike(franchiseId);
    setFavorites(updatedFavorites);
  };

  const getFranchiseCategoryLabel = (category: franchiseCategory | string) => {
    return (
      franchiseCategory[category as keyof typeof franchiseCategory] || '기타'
    );
  };

  const handleNavigateMap = (
    latitude: number,
    longitude: number,
    franchiseId: number,
  ) => {
    navigate('/map', {
      state: {
        latitude: latitude,
        longitude: longitude,
        franchiseId: franchiseId,
      },
    });
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
                <button onClick={() => handleHeartClick(franchise.franchiseId)}>
                  {favorites.find((f) => f.id === franchise.franchiseId)
                    ?.isFavorite ? (
                    <HeartSolidIcon className="size-8 text-RED" />
                  ) : (
                    <HeartOutlineIcon className="size-8 text-RED" />
                  )}
                </button>
                <div className="ml-4">
                  <div className="flex items-center gap-2">
                    <h3 className="w-36 truncate text-lg font-semibold">
                      {franchise.franchiseName}
                    </h3>
                    <span className="text-base text-DARKBASE">
                      {getFranchiseCategoryLabel(franchise.category)}
                    </span>
                  </div>
                  <div className="text-sm">{franchise.address}</div>
                </div>
              </div>
              <button
                onClick={() =>
                  handleNavigateMap(
                    franchise.latitude,
                    franchise.longitude,
                    franchise.franchiseId,
                  )
                }
              >
                <ChevronRightIcon className="size-6" />
              </button>
            </div>
          ))}
        </section>
      </main>
    </Layout>
  );
};

export default BookmarkPage;
