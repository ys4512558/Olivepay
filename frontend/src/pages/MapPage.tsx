import { useState, useEffect } from 'react';
import { useAtom } from 'jotai';
import {
  Input,
  Layout,
  Button,
  BackButton,
  PageTitle,
  BottomUp,
  Card,
} from '../component/common';
import {
  CategorySelector,
  FranchiseDetail,
  FranchiseMap,
} from '../component/franchise';
import { franchiseDetailAtom, franchiseListAtom } from '../atoms/franchiseAtom';
import clsx from 'clsx';
import { getFranchiseDetail } from '../api/franchiseApi';

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
  }, []);

  const handleSearch = () => {
    setSubmitTerm(searchTerm);
  };

  const handleDetail = async (
    lat: number,
    lon: number,
    franchiseId: number,
  ) => {
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
  };

  const handleContent = () => {
    setFranchise(null);
  };

  const handleCategoryClick = (category: string) => {
    setSelectedCategory(category === selectedCategory ? null : category);
  };

  return (
    <>
      <Layout>
        <header className="mx-8 mt-12 flex items-center justify-between">
          <BackButton />
          <PageTitle title="식당 검색" />
          <div className="w-8" />
        </header>
        <section className="mx-8 flex items-center gap-2 py-8">
          <Input
            value={searchTerm}
            onChange={(e) => setSearchTerm(e.target.value)}
            className="w-full border-2"
          />
          <Button variant="secondary" onClick={handleSearch} label="검색" />
        </section>
        <section className="relative">
          <CategorySelector
            selectedCategory={selectedCategory}
            handleCategoryClick={handleCategoryClick}
          />
          <FranchiseMap
            location={location}
            franchises={franchises}
            searchTerm={submitTerm}
            setLocation={setLocation}
            onClick={handleDetail}
          />
        </section>
      </Layout>
      <BottomUp
        isVisible={isBottomUpVisible}
        setIsVisible={setIsBottomUpVisible}
        className={clsx(
          'overflow-scroll scrollbar-hide',
          franchise ? 'h-[600px]' : 'h-96',
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
                        franchise.franchiseId,
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
