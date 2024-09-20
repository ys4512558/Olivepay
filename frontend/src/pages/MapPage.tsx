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
import { FranchiseMap } from '../component/franchise';
import { franchiseListAtom } from '../atoms/franchiseAtom';

interface Location {
  latitude: number;
  longitude: number;
}

const MapPage = () => {
  const [franchises] = useAtom(franchiseListAtom);

  const [location, setLocation] = useState<Location>({
    latitude: 0,
    longitude: 0,
  });
  const [error, setError] = useState<string | null>(null);
  const [searchTerm, setSearchTerm] = useState('');
  const [submitTerm, setSubmitTerm] = useState('');
  const [isBottomUpVisible, setIsBottomUpVisible] = useState(false);

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

  const handleDetail = (lat: number, lon: number) => {
    setLocation({
      latitude: lat,
      longitude: lon,
    });
    setIsBottomUpVisible(false);
  };

  return (
    <>
      <Layout>
        <header className="mx-8 mt-12 flex items-center justify-between">
          <BackButton />
          <PageTitle title="식당 검색" />
          <div className="w-8" />
        </header>
        <section className="mx-8 my-8 flex items-center gap-2">
          <Input
            value={searchTerm}
            onChange={(e) => setSearchTerm(e.target.value)}
            className="w-full border-2"
          />
          <Button variant="secondary" onClick={handleSearch} label="검색" />
        </section>
        <section>
          {error ? (
            <p>{error}</p>
          ) : (
            <FranchiseMap
              location={location}
              franchises={franchises}
              searchTerm={submitTerm}
              setLocation={setLocation}
            />
          )}
        </section>
      </Layout>
      <BottomUp
        isVisible={isBottomUpVisible}
        setIsVisible={setIsBottomUpVisible}
        className="h-[600px] overflow-scroll scrollbar-hide"
        children={
          <>
            {franchises.map((franchise) => (
              <div key={franchise.franchiseId} className="mb-2">
                <Card
                  variant="restaurant"
                  title={franchise.franchiseName}
                  category={franchise.category}
                  score={franchise.avgStars}
                  like={franchise.likes}
                  onClick={() =>
                    handleDetail(franchise.latitude, franchise.longitude)
                  }
                />
              </div>
            ))}
          </>
        }
      />
    </>
  );
};

export default MapPage;
