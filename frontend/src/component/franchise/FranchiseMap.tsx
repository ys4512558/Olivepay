import { useState, useEffect } from 'react';
import { Map, MapMarker } from 'react-kakao-maps-sdk';
import { restaurants } from '../../types/franchise';
import { GlobeAltIcon } from '@heroicons/react/24/outline';
import { franchiseCategory } from '../../types/franchise';
import { getFranchises } from '../../api/franchiseApi';
import { enqueueSnackbar } from 'notistack';

interface LocationProps {
  location: {
    latitude: number;
    longitude: number;
  };
  franchises: restaurants;
  searchTerm: string;
  setSearchTerm: (searchTerm: string) => void;
  setSubmitTerm: (submitTerm: string) => void;
  setLocation: (location: { latitude: number; longitude: number }) => void;
  onClick: (lat: number, lon: number, franchiseId: number) => void;
  // onSearch: () => void;
  selectedCategory: franchiseCategory | null;
  setFranchise: (el: null) => void;
  setFranchises: (el: restaurants) => void;
  setSelectedCategory: (el: null) => void;
}

const FranchiseMap: React.FC<LocationProps> = ({
  location,
  franchises,
  searchTerm,
  setSearchTerm,
  setSubmitTerm,
  setLocation,
  onClick,
  // onSearch,
  selectedCategory,
  setSelectedCategory,
  setFranchise,
  setFranchises,
}) => {
  const [map, setMap] = useState<kakao.maps.Map | null>(null);
  const [showSearchButton, setShowSearchButton] = useState(false);
  const [currentLocation, setCurrentLocation] = useState<
    LocationProps['location'] | null
  >(null);

  useEffect(() => {
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition(
        (position) => {
          const userLocation = {
            latitude: position.coords.latitude,
            longitude: position.coords.longitude,
          };
          setCurrentLocation(userLocation);
          // setLocation(userLocation);
          // map?.setCenter(
          //   new kakao.maps.LatLng(
          //     userLocation.latitude,
          //     userLocation.longitude,
          //   ),
          // );
        },
        () => {
          alert('위치 정보를 사용할 수 없습니다.');
        },
      );
    } else {
      alert('호환되지 않는 브라우저입니다.');
    }
  }, [map, setLocation]);

  useEffect(() => {
    if (!map || !searchTerm) return;

    const ps = new kakao.maps.services.Places();

    ps.keywordSearch(searchTerm, async (data, status) => {
      if (status === kakao.maps.services.Status.OK && data.length > 0) {
        const firstPlace = data[0];
        const newLocation = {
          latitude: parseFloat(firstPlace.y),
          longitude: parseFloat(firstPlace.x),
        };
        setLocation(newLocation);
        setSearchTerm('');
        setSubmitTerm('');
        map.setCenter(
          new kakao.maps.LatLng(newLocation.latitude, newLocation.longitude),
        );
        const categoryKey = selectedCategory
          ? (
              Object.keys(franchiseCategory) as Array<
                keyof typeof franchiseCategory
              >
            ).find((key) => franchiseCategory[key] === selectedCategory)
          : undefined;

        try {
          const result = await getFranchises(
            newLocation.latitude,
            newLocation.longitude,
            categoryKey,
          );
          setFranchises(result);
          setFranchise(null);
        } catch {
          enqueueSnackbar('가맹점 정보를 불러오는 중 오류가 발생했습니다.', {
            variant: 'error',
          });
        }
      } else {
        enqueueSnackbar('검색 결과가 없습니다.', { variant: 'info' });
      }
    });
  }, [
    searchTerm,
    setSearchTerm,
    setSubmitTerm,
    map,
    setLocation,
    selectedCategory,
    setFranchise,
    setFranchises,
  ]);

  const handleMapDragEnd = () => {
    const center = map?.getCenter();
    if (center) {
      const newLocation = {
        latitude: center.getLat(),
        longitude: center.getLng(),
      };
      setLocation(newLocation);
      setShowSearchButton(true);
    }
  };

  const moveToCurrentLocation = async () => {
    if (navigator.geolocation) {
      try {
        const position = await new Promise<GeolocationPosition>(
          (resolve, reject) => {
            navigator.geolocation.getCurrentPosition(resolve, reject);
          },
        );

        const currentLocation = {
          latitude: position.coords.latitude,
          longitude: position.coords.longitude,
        };
        setLocation(currentLocation);
        map?.setCenter(
          new kakao.maps.LatLng(
            currentLocation.latitude,
            currentLocation.longitude,
          ),
        );

        try {
          const result = await getFranchises(
            currentLocation.latitude,
            currentLocation.longitude,
          );
          setFranchises(result);
          setFranchise(null);
          setSelectedCategory(null);
        } catch {
          enqueueSnackbar('가맹점 정보를 불러오는 중 오류가 발생했습니다.', {
            variant: 'error',
          });
        }

        setShowSearchButton(false);
        setSearchTerm('');
      } catch {
        enqueueSnackbar('위치 정보를 사용할 수 없습니다.', {
          variant: 'warning',
        });
      }
    } else {
      enqueueSnackbar('호환되지 않는 브라우저입니다.', { variant: 'warning' });
    }
  };

  const handleSearchByMap = async () => {
    if (location) {
      try {
        const categoryKey = selectedCategory
          ? (
              Object.keys(franchiseCategory) as Array<
                keyof typeof franchiseCategory
              >
            ).find((key) => franchiseCategory[key] === selectedCategory)
          : undefined;

        const results = await getFranchises(
          location.latitude,
          location.longitude,
          categoryKey,
        );
        // onSearch();
        setFranchises(results);
        setShowSearchButton(false);
      } catch {
        enqueueSnackbar('가맹점 정보를 불러오는 중 오류가 발생했습니다.', {
          variant: 'error',
        });
      }
    }
  };

  return (
    <div className="relative">
      <Map
        className="z-0 h-[93dvh] w-full border-t-2"
        center={{ lat: location.latitude, lng: location.longitude }}
        onCreate={setMap}
        minLevel={5}
        onDragEnd={handleMapDragEnd}
      >
        {currentLocation && (
          <MapMarker
            position={{
              lat: currentLocation.latitude,
              lng: currentLocation.longitude,
            }}
            image={{
              src: '/image/userLocation.svg',
              size: {
                width: 30,
                height: 30,
              },
            }}
          />
        )}
        {franchises.map((marker) => (
          <MapMarker
            key={marker.franchiseId}
            position={{ lat: marker.latitude, lng: marker.longitude }}
            onClick={() =>
              onClick(marker.latitude, marker.longitude, marker.franchiseId)
            }
            image={{
              src:
                marker.coupons === 0
                  ? '/image/marker_none.svg'
                  : '/image/marker.svg',
              size: {
                width: 40,
                height: 40,
              },
            }}
          />
        ))}
      </Map>

      <button
        onClick={moveToCurrentLocation}
        className="absolute right-2 top-28 z-10 rounded-full bg-white p-3 shadow-lg"
        title="현재 위치로"
      >
        <GlobeAltIcon className="size-6 text-DARKBASE" />
      </button>

      {showSearchButton && (
        <button
          onClick={handleSearchByMap}
          className="absolute bottom-24 left-1/2 z-10 flex -translate-x-1/2 rounded-full border-2 bg-white px-4 py-2 text-base font-semibold shadow-lg"
        >
          이 위치로 검색
        </button>
      )}
    </div>
  );
};

export default FranchiseMap;
