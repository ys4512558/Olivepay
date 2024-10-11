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
with open('olivepay_user_data.csv', 'rb') as f:
    result = chardet.detect(f.read())

# 감지된 인코딩으로 파일 읽기
df = pd.read_csv('olivepay_user_data.csv', dtype=str, encoding=result['encoding'])

# 각 행에 대해 POST 요청 보내기
for index, row in df.iterrows():
    # 요청할 데이터 준비
    name = row['name']
    password = row['password']
    phoneNumber = row['phoneNumber']
    nickname = row['nickname']
    birthdate = row['birthdate']
    pin = row['pin']
    realCardNumber = row['realCardNumber']
    expirationYear = row['expirationYear']
    expirationMonth = row['expirationMonth']
    cvc = row['cvc']
    creditPassword = row['creditPassword']
    diffCardNumber = row['diffCardNumber']

    
    
    # 유저 회원가입 ###############################################################################################
    register_data = {
        "name": name,
        "password": password,
        "phoneNumber": phoneNumber,
        "nickname": nickname,
        "birthdate": birthdate,
        "pin": pin
    }

    try:
        response = requests.post("http://j11a601.p.ssafy.io:8000/api/members/users/sign-up", json=register_data)
        response.raise_for_status()  # HTTP 오류가 발생하면 예외를 발생시킴
        logging.info(f"Successfully registered user: {phoneNumber}")

    except requests.exceptions.HTTPError as err:
        # 응답 바이트를 UTF-8로 디코딩
        response_content = response.content.decode('utf-8')  # 한글로 보이게 하기 위한 디코딩
        logging.error(f"HTTP error occurred for {row['phoneNumber']}: {err} for url: {response.url}")
        logging.error(f"Response content: {response_content}")  # 에러 메시지 로그
    
    
    # 서버에 과부하가 걸리지 않도록 딜레이 추가
    time.sleep(0.1)

    # 임시 유저 로그인 ###############################################################################################
    login_data = {
        "phoneNumber": phoneNumber,
        "password": password
    }

    try:
        response = requests.post("http://j11a601.p.ssafy.io:8000/api/auths/users/login", json=login_data)
        response.raise_for_status()  # HTTP 오류가 발생하면 예외를 발생시킴
        logging.info(f"Successfully login user: {phoneNumber}")
        token = response.json().get('data').get('accessToken')

    except requests.exceptions.HTTPError as err:
        # 응답 바이트를 UTF-8로 디코딩
        response_content = response.content.decode('utf-8')  # 한글로 보이게 하기 위한 디코딩
        logging.error(f"HTTP error occurred for {row['phoneNumber']}: {err} for url: {response.url}")
        logging.error(f"Response content: {response_content}")  # 에러 메시지 로그

    # 서버에 과부하가 걸리지 않도록 딜레이 추가
    time.sleep(0.1)


    # 유저 카드 등록 ###############################################################################################
    headers = {
        "Authorization": f"Bearer {token}"  # Bearer 토큰 추가
    }

    tree_card_data = {
        "realCardNumber": realCardNumber,
        "expirationYear": expirationYear,
        "expirationMonth": expirationMonth,
        "cvc": cvc,
        "creditPassword": creditPassword
    }

    try:
        response = requests.post("http://j11a601.p.ssafy.io:8000/api/cards", json=tree_card_data, headers=headers)
        response.raise_for_status()  # HTTP 오류가 발생하면 예외를 발생시킴
        logging.info(f"Successfully registered tree card: {phoneNumber}")
    except requests.exceptions.HTTPError as err:
        # 응답 바이트를 UTF-8로 디코딩
        response_content = response.content.decode('utf-8')  # 한글로 보이게 하기 위한 디코딩
        logging.error(f"HTTP error occurred for {row['phoneNumber']}: {err} for url: {response.url}")
        logging.error(f"Response content: {response_content}")  # 에러 메시지 로그

    # 서버에 과부하가 걸리지 않도록 딜레이 추가
    time.sleep(0.1)


    # 일반 유저 로그인 ###############################################################################################
    login_data = {
        "phoneNumber": phoneNumber,
        "password": password
    }

    try:
        response = requests.post("http://j11a601.p.ssafy.io:8000/api/auths/users/login", json=login_data)
        response.raise_for_status()  # HTTP 오류가 발생하면 예외를 발생시킴
        logging.info(f"Successfully login user: {phoneNumber}")
        token = response.json().get('data').get('accessToken')

    except requests.exceptions.HTTPError as err:
        # 응답 바이트를 UTF-8로 디코딩
        response_content = response.content.decode('utf-8')  # 한글로 보이게 하기 위한 디코딩
        logging.error(f"HTTP error occurred for {row['phoneNumber']}: {err} for url: {response.url}")
        logging.error(f"Response content: {response_content}")  # 에러 메시지 로그

    # 서버에 과부하가 걸리지 않도록 딜레이 추가
    time.sleep(0.1)


    # 일반 카드 등록 ###############################################################################################
    headers = {
        "Authorization": f"Bearer {token}"  # Bearer 토큰 추가
    }

    common_card_data = {
        "realCardNumber": diffCardNumber,
        "expirationYear": expirationYear,
        "expirationMonth": expirationMonth,
        "cvc": cvc,
        "creditPassword": creditPassword
    }

    try:
        response = requests.post("http://j11a601.p.ssafy.io:8000/api/cards", json=common_card_data, headers=headers)
        response.raise_for_status()  # HTTP 오류가 발생하면 예외를 발생시킴
        logging.info(f"Successfully registered common card: {phoneNumber}")
    except requests.exceptions.HTTPError as err:
        # 응답 바이트를 UTF-8로 디코딩
        response_content = response.content.decode('utf-8')  # 한글로 보이게 하기 위한 디코딩
        logging.error(f"HTTP error occurred for {row['phoneNumber']}: {err} for url: {response.url}")
        logging.error(f"Response content: {response_content}")  # 에러 메시지 로그

    # 서버에 과부하가 걸리지 않도록 딜레이 추가
    time.sleep(0.1)


    # 가맹점 좋아요 ###############################################################################################
    if index == 0:  # 첫 번째 사람은 건너뜁니다.
        continue

    headers = {
        "Authorization": f"Bearer {token}"  # Bearer 토큰 추가
    }

    for _ in range(100):
        franchise_id = random.randint(1, 6000)  # 1부터 6000 사이의 랜덤 정수 생성
        try:
            response = requests.post(f"http://j11a601.p.ssafy.io:8000/api/franchises/likes/user/{franchise_id}", headers=headers)
            response.raise_for_status()  # HTTP 오류가 발생하면 예외를 발생시킴
            logging.info(f"Successfully registered like: {phoneNumber} {franchise_id}")
        except requests.exceptions.HTTPError as err:
            # 응답 바이트를 UTF-8로 디코딩
            response_content = response.content.decode('utf-8')  # 한글로 보이게 하기 위한 디코딩
            logging.error(f"HTTP error occurred for {row['phoneNumber']}: {err} for url: {response.url}")
            logging.error(f"Response content: {response_content}")  # 에러 메시지 로그

        # 서버에 과부하가 걸리지 않도록 딜레이 추가
        time.sleep(0.1)
