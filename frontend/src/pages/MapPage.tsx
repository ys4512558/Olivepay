import { useState, useEffect } from 'react';
import { useAtom } from 'jotai';
import { Input, Layout, Button, BottomUp, Card } from '../component/common';
import {
  CategorySelector,
  FranchiseDetail,
  FranchiseMap,
} from '../component/franchise';
import { franchiseDetailAtom, franchiseListAtom } from '../atoms/franchiseAtom';
import clsx from 'clsx';
import {
  // getFranchiseDetail,
  getFranchises,
} from '../api/franchiseApi';

interface Location {
  latitude: number;
  longitude: number;
}

const MapPage = () => {
  const [franchises] = useAtom(franchiseListAtom);
  const [franchise, setFranchise] = useAtom(franchiseDetailAtom);
  const [location, setLocation] = useState<Location>({
    latitude: 0,
    longitude: 0,
  });
  const [error, setError] = useState<string | null>(null);
  const [searchTerm, setSearchTerm] = useState('');
  const [submitTerm, setSubmitTerm] = useState('');
  const [isBottomUpVisible, setIsBottomUpVisible] = useState(false);
  const [selectedCategory, setSelectedCategory] = useState<string | null>(null);

  useEffect(() => {
    console.log(error);
  }, []);

  useEffect(() => {
    const getLocation = () => {
      if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(
          (position) => {
            setLocation({
              latitude: position.coords.latitude,
              longitude: position.coords.longitude,
            });
          },
          (err) => {
            setError(err.message);
          },
        );
      } else {
        setError('Geolocation is not supported by this browser.');
      }
    };
    getLocation();
    // getFranchises(location.latitude, location.longitude);
  }, []);

  const handleSearch = () => {
    setSubmitTerm(searchTerm);
    setSearchTerm('');
  };

  const handleDetail = async (
    lat: number,
    lon: number,
    // franchiseId: number,
  ) => {
    setLocation({
      latitude: lat,
      longitude: lon,
    });
    setIsBottomUpVisible(false);
    // const franchiseDetail = await getFranchiseDetail(franchiseId);
    // setFranchise(franchiseDetail);
    setTimeout(() => {
      setIsBottomUpVisible(true);
    }, 500);
  };

  const handleContent = () => {
    setFranchise(null);
  };

  const handleCategoryClick = (category: string) => {
    setSelectedCategory(category === selectedCategory ? null : category);
    // getFranchises(
    //   location.latitude,
    //   location.longitude,
    //   franchiseCategory[category as keyof typeof franchiseCategory],
    // );
  };

  return (
    <>
      <Layout>
        <section className="relative">
          <div className="absolute top-3 z-20 flex w-full items-center gap-2 px-2">
            <Input
              value={searchTerm}
              onChange={(e) => setSearchTerm(e.target.value)}
              className="h-9 w-full border-2"
              placeholder="원하는 지역을 검색해보세요"
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
            searchTerm={submitTerm}
            setSearchTerm={setSearchTerm}
            setLocation={setLocation}
            onClick={handleDetail}
            onSearch={() =>
              getFranchises(location.latitude, location.longitude)
            }
          />
        </section>
      </Layout>
      <BottomUp
        isVisible={isBottomUpVisible}
        setIsVisible={setIsBottomUpVisible}
        className={clsx(
          'overflow-scroll scrollbar-hide',
          franchise ? 'h-[75dvh]' : 'h-[48dvh]',
        )}
        children={
          <>
            {!franchise &&
              franchises.map((franchise) => (
                <div key={franchise.franchiseId} className="mb-4">
                  <Card
                    variant="restaurant"
                    title={franchise.franchiseName}
                    category={franchise.category}
                    score={franchise.avgStars}
                    like={franchise.likes}
                    onClick={() =>
                      handleDetail(
                        franchise.latitude,
                        franchise.longitude,
                        // franchise.franchiseId,
                      )
                    }
                  />
                </div>
              ))}
            {franchise && (
              <FranchiseDetail franchise={franchise} onClick={handleContent} />
            )}
          </>
        }
      />
    </>
  );
};

export default MapPage;
