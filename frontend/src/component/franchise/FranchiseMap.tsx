import { useState, useEffect } from 'react';
import { Map, MapMarker } from 'react-kakao-maps-sdk';
import { restaurants } from '../../types/franchise';
import { GlobeAltIcon } from '@heroicons/react/24/outline';

interface LocationProps {
  location: {
    latitude: number;
    longitude: number;
  };
  franchises: restaurants;
  searchTerm: string;
  setLocation: (location: { latitude: number; longitude: number }) => void;
}

const FranchiseMap: React.FC<LocationProps> = ({
  location,
  franchises,
  searchTerm,
  setLocation,
}) => {
  const [map, setMap] = useState<kakao.maps.Map | null>(null);

  useEffect(() => {
    if (!map || !searchTerm) return;

    const ps = new kakao.maps.services.Places();

    ps.keywordSearch(searchTerm, (data, status) => {
      if (status === kakao.maps.services.Status.OK && data.length > 0) {
        const firstPlace = data[0];
        const newLocation = {
          latitude: parseFloat(firstPlace.y),
          longitude: parseFloat(firstPlace.x),
        };
        console.log(newLocation);
        setLocation(newLocation);
        map.setCenter(
          new kakao.maps.LatLng(newLocation.latitude, newLocation.longitude),
        );
      } else {
        alert('검색 결과가 없습니다.');
      }
    });
  }, [searchTerm, map, setLocation]);

  const moveToCurrentLocation = () => {
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition(
        (position) => {
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
        },
        // (error) => {
        //   alert('위치 정보를 사용할 수 없습니다.');
        // },
      );
    } else {
      alert('호환되지 않는 브라우저입니다.');
    }
  };
  return (
    <div className="relative">
      <Map
        className="z-0 h-[640px] w-full border-t-2"
        center={{ lat: location.latitude, lng: location.longitude }}
        onCreate={setMap}
        minLevel={4}
      >
        {franchises.map((marker) => (
          <MapMarker
            key={marker.franchiseId}
            position={{ lat: marker.latitude, lng: marker.longitude }}
          />
        ))}
      </Map>

      <button
        onClick={moveToCurrentLocation}
        className="absolute bottom-14 right-4 z-10 rounded-full bg-white p-3 shadow-lg"
        title="현재 위치로"
      >
        <GlobeAltIcon className="size-6 text-DARKBASE" />
      </button>
    </div>
  );
};

export default FranchiseMap;
