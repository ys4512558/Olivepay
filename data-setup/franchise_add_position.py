import pandas as pd
import requests
import xml.etree.ElementTree as ET
import time
import logging
import chardet

### 가맹점 주소를 통해 위/경도 데이터를 추가합니다.

# 로깅 설정
logging.basicConfig(level=logging.INFO, format='%(asctime)s - %(levelname)s - %(message)s')

# API 설정
apiurl = "https://api.vworld.kr/req/address?"
api_key = "API_KEY"

# 파일의 인코딩 감지
with open('olivepay_franchise_data.csv', 'rb') as f:
    result = chardet.detect(f.read())

# 감지된 인코딩으로 파일 읽기
df = pd.read_csv('olivepay_franchise_data.csv', dtype=str, encoding=result['encoding'])

# 모든 데이터의 앞뒤 공백 제거
df = df.applymap(lambda x: x.strip() if isinstance(x, str) else x)


# 함수 정의: 주소에서 위도와 경도를 가져오는 함수
def get_lat_long(address):
    params = {
        "service": "address",
        "request": "getcoord",
        "crs": "epsg:4326",
        "address": address,
        "format": "xml",  # XML 형식으로 요청
        "type": "road",
        "key": api_key
    }
    headers = {
        "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36"
    }
    
    try:
        time.sleep(0.2)  # 요청 간 대기 시간
        response = requests.get(apiurl, params=params, headers=headers)
        response.raise_for_status()  # HTTP 오류 발생 시 예외 발생
        # XML 파싱
        root = ET.fromstring(response.content)
        # 위도와 경도 추출
        point = root.find('.//point')
        if point is not None:
            longitude = point.find('x').text
            latitude = point.find('y').text
            return latitude, longitude
        else:
            return None, None  # 결과가 없을 경우 None 반환
    except requests.exceptions.HTTPError as err:
        logging.error(f"HTTP error occurred: {err}")
        return None, None
    except Exception as e:
        logging.error(f"An error occurred: {e}")
        return None, None

# 각 행을 반복하면서 위도와 경도 업데이트
error_count = 0
success_count = 0
for index in range(len(df)):
    if pd.isna(df.at[index, 'latitude']) or pd.isna(df.at[index, 'longitude']):  # 비어있는 경우에만
        latitude, longitude = get_lat_long(df.at[index, 'address'])
        df.at[index, 'latitude'] = latitude
        df.at[index, 'longitude'] = longitude
    else:
        success_count = success_count+1
        continue
        
    # 진행 상황 로그    
    if pd.isna(df.at[index, 'latitude']) or pd.isna(df.at[index, 'longitude']):  # 데이터 삽입이 제대로 이루어지지 않은 경우
        logging.error(f"{index + 1}번의 데이터를 처리하지 못했습니다.")
        error_count = error_count+1
    else:
        logging.info(f"{index + 1}번의 데이터를 처리하였습니다.")
        success_count = success_count+1

# 최종 데이터 저장
df.to_csv('olivepay_franchise_data.csv', index=False)
logging.info(f"{success_count}개의 데이터를 처리하고 최종적으로 저장하였습니다.")
if error_count > 0:
    logging.info(f"{error_count}개의 데이터를 처리하지 못했습니다. 다시 코드를 실행하세요")