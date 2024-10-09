import { useState, useEffect, useCallback } from 'react';
import { useAtom } from 'jotai';
import { useLocation } from 'react-router-dom';
import {
  Input,
  Layout,
  Button,
  BottomUp,
  Card,
  EmptyData,
} from '../component/common';
import {
  CategorySelector,
  FranchiseDetail,
  FranchiseMap,
} from '../component/franchise';
import { franchiseDetailAtom, franchiseListAtom } from '../atoms/franchiseAtom';
import clsx from 'clsx';
import { getFranchiseDetail, getFranchises } from '../api/franchiseApi';
import { franchiseCategory } from '../types/franchise';
import { getFranchiseCategoryEmoji } from '../utils/category';
import { useSnackbar } from 'notistack';
import { Helmet } from 'react-helmet';

interface Location {
  latitude: number;
  longitude: number;
}

const MapPage = () => {
  const { enqueueSnackbar } = useSnackbar();
  const { state } = useLocation();
  const [franchises, setFranchises] = useAtom(franchiseListAtom);
  const [franchise, setFranchise] = useAtom(franchiseDetailAtom);
  const [location, setLocation] = useState<Location>({
    latitude: 0,
    longitude: 0,
  });
  const [error, setError] = useState<string | null>(null);
  const [searchTerm, setSearchTerm] = useState('');
  const [submitTerm, setSubmitTerm] = useState('');
  const [isBottomUpVisible, setIsBottomUpVisible] = useState(false);
  const [selectedCategory, setSelectedCategory] =
    useState<franchiseCategory | null>(null);

  useEffect(() => {
    if (state?.status) {
      console.log('현재 상태:', state.status);
    }
  }, [state]);

  useEffect(() => {
    const getLocation = async () => {
      if (state?.latitude && state?.longitude && state?.franchiseId) {
        setLocation({ latitude: state.latitude, longitude: state.longitude });

        try {
          const results = await getFranchises(state.latitude, state.longitude);
          setFranchises(results);
          const result = await getFranchiseDetail(state.franchiseId);
          setFranchise(result);
          setIsBottomUpVisible(true);
        } catch {
          enqueueSnackbar(
            '프랜차이즈 정보를 불러오는 중 오류가 발생했습니다.',
            {
              variant: 'error',
            },
          );
        }
      } else {
        if (navigator.geolocation) {
          navigator.geolocation.getCurrentPosition(
            async (position) => {
              const userLocation = {
                latitude: position.coords.latitude,
                longitude: position.coords.longitude,
              };
              setLocation(userLocation);

              try {
                const franchises = await getFranchises(
                  userLocation.latitude,
                  userLocation.longitude,
                );
                setFranchises(franchises);
              } catch {
                enqueueSnackbar(
                  '가맹점 정보를 불러오는 중 오류가 발생했습니다.',
                  {
                    variant: 'error',
                  },
                );
              }
            },
            (err) => {
              setError(err.message);
            },
          );
        } else {
          enqueueSnackbar('현재 위치를 확인할 수 없습니다.', {
            variant: 'error',
          });
        }
      }
    };

    getLocation();
  }, [state, setLocation, setFranchise, setFranchises, enqueueSnackbar]);

  useEffect(() => {
    if (error) {
      enqueueSnackbar(error, {
        variant: 'error',
      });
    }
  }, [error, enqueueSnackbar]);

  useEffect(() => {
    return () => {
      setFranchise(null);
    };
  }, [setFranchise]);

  const getCategoryKey = (
    selectedCategory: franchiseCategory | null,
  ): keyof typeof franchiseCategory | undefined => {
    if (!selectedCategory) return undefined;
    return (
      Object.keys(franchiseCategory) as Array<keyof typeof franchiseCategory>
    ).find((key) => franchiseCategory[key] === selectedCategory);
  };

  const handleSearch = useCallback(() => {
    setSubmitTerm(searchTerm);
    setSearchTerm('');
  }, [searchTerm]);

  const handleDetail = useCallback(
    async (lat: number, lon: number, franchiseId: number) => {
      setLocation({
        latitude: lat,
        longitude: lon,
      });
      setIsBottomUpVisible(false);
      const franchiseDetail = await getFranchiseDetail(franchiseId);
      setFranchise(franchiseDetail);
      setTimeout(() => {
        setIsBottomUpVisible(true);
      }, 500);
    },
    [setFranchise],
  );

  const handleContent = useCallback(() => {
    setFranchise(null);
  }, [setFranchise]);

  const handleCategoryClick = useCallback(
    async (category: franchiseCategory) => {
      const newCategory = category === selectedCategory ? null : category;
      setSelectedCategory(newCategory);

      const matchedCategoryKey = getCategoryKey(newCategory);

      try {
        const results = await getFranchises(
          location.latitude,
          location.longitude,
          matchedCategoryKey,
        );
        setFranchises(results);
      } catch {
        enqueueSnackbar('가맹점 정보를 불러오는 중 오류가 발생했습니다.', {
          variant: 'error',
        });
      }
    },
    [
      selectedCategory,
      location.latitude,
      location.longitude,
      enqueueSnackbar,
      setFranchises,
    ],
  );

  return (
    <>
      <Helmet>
        <meta
          name="description"
          content="가맹점을 확인하고 가맹점을 찜할 수 있습니다."
        />
      </Helmet>
      <Layout>
        <section className="relative">
          <div className="absolute top-3 z-20 flex w-full items-center gap-2 px-2">
            <Input
              value={searchTerm}
              onChange={(e) => setSearchTerm(e.target.value)}
              className="h-9 w-full border-2"
              placeholder="원하는 지역을 검색해보세요"
              onKeyDown={(e) => {
                if (e.key === 'Enter') {
                  handleSearch();
                }
              }}
            />
            <Button
              variant="secondary"
              onClick={handleSearch}
              label="검색"
              className="h-8"
            />
          </div>

          <CategorySelector
            selectedCategory={selectedCategory}
            handleCategoryClick={handleCategoryClick}
          />
          <FranchiseMap
            location={location}
            franchises={franchises}
            setFranchise={setFranchise}
            searchTerm={submitTerm}
            setSearchTerm={setSearchTerm}
            setSubmitTerm={setSubmitTerm}
            setLocation={setLocation}
            onClick={handleDetail}
            // onSearch={handleSearchFranchises}
            selectedCategory={selectedCategory}
            setSelectedCategory={setSelectedCategory}
            setFranchises={setFranchises}
          />
        </section>
      </Layout>
      <BottomUp
        isVisible={isBottomUpVisible}
        setIsVisible={setIsBottomUpVisible}
        className={clsx(
          'overflow-scroll pb-20 scrollbar-hide',
          franchise ? 'h-[75dvh]' : 'h-[52dvh]',
        )}
        children={
          <div className="mb-12">
            {!franchise &&
              franchises?.map((franchise) => {
                const categoryKey =
                  franchise.category as keyof typeof franchiseCategory;
                return (
                  <div key={franchise.franchiseId} className="mb-4">
                    <Card
                      variant="restaurant"
                      title={franchise.franchiseName}
                      category={
                        franchiseCategory[categoryKey] +
                        getFranchiseCategoryEmoji(
                          franchiseCategory[categoryKey],
                        )
                      }
                      score={
                        franchise.avgStars !== 0
                          ? +franchise.avgStars.toFixed(2)
                          : 0
                      }
                      like={franchise.likes || '0'}
                      onClick={() =>
                        handleDetail(
                          franchise.latitude,
                          franchise.longitude,
                          franchise.franchiseId,
                        )
                      }
                    />
                  </div>
                );
              })}
            {!franchise && franchises?.length === 0 && (
              <EmptyData label="조건에 맞는 가맹점이 없습니다." />
            )}
            {franchise && (
              <FranchiseDetail
                franchise={franchise}
                state={state?.status}
                onClick={handleContent}
              />
            )}
          </div>
        }
      />
    </>
  );
};

export default MapPage;
