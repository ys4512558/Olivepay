import pandas as pd
import requests
import xml.etree.ElementTree as ET
import time
import logging
import chardet
import random

# 로깅 설정
logging.basicConfig(level=logging.INFO, format='%(asctime)s - %(levelname)s - %(message)s')

# 파일의 인코딩 감지
with open('olivepay_franchise_data.csv', 'rb') as f:
    result = chardet.detect(f.read())

# 감지된 인코딩으로 파일 읽기
df = pd.read_csv('olivepay_franchise_data.csv', dtype=str, encoding=result['encoding'])

# 각 행에 대해 POST 요청 보내기
for index, row in df.iterrows():
    # 요청할 데이터 준비
    name = row['name']
    password = row['password']
    phoneNumber = row['phoneNumber']
    registrationNumber = row['registrationNumber']
    franchiseName = row['franchiseName']
    category = row['category']
    telephoneNumber = row['telephoneNumber'].strip('"')  # 앞뒤의 " 제거
    address = row['address'].strip('"')
    latitude = float(row['latitude'])
    longitude = float(row['longitude'])
    rrnPrefix = row['rrnPrefix']
    rrnCheckDigit = int(row['rrnCheckDigit'])

    # # telephoneNumber 처리
    # if len(telephoneNumber) < 10:
    #     # 0~9 사이 임의의 숫자 추가
    #     telephoneNumber += str(random.randint(0, 9))
    # elif len(telephoneNumber) > 11:
    #     # 맨 뒤 숫자 잘라서 11자리로 맞추기
    #     telephoneNumber = telephoneNumber[:11]

    data = {
        "name": name,
        "password": password,
        "phoneNumber": phoneNumber,
        "registrationNumber": registrationNumber,
        "franchiseName": franchiseName,
        "category": category,
        "telephoneNumber": telephoneNumber,
        "address": address,
        "latitude": latitude,
        "longitude": longitude,
        "rrnPrefix": rrnPrefix,
        "rrnCheckDigit": rrnCheckDigit
    }
    
    try:
        response = requests.post("http://j11a601.p.ssafy.io:8000/api/members/owners/sign-up", json=data)
        response.raise_for_status()  # HTTP 오류가 발생하면 예외를 발생시킴
        logging.info(f"Successfully registered franchise: {franchiseName} {phoneNumber}")

    except requests.exceptions.HTTPError as err:
        # 응답 바이트를 UTF-8로 디코딩
        response_content = response.content.decode('utf-8')  # 한글로 보이게 하기 위한 디코딩
        logging.error(f"HTTP error occurred for {row['franchiseName']}: {err} for url: {response.url}")
        logging.error(f"Response content: {response_content}")  # 에러 메시지 로그
    
    
    # 서버에 과부하가 걸리지 않도록 딜레이 추가
    time.sleep(0.1)