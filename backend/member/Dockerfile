FROM openjdk:17-jdk-buster AS builder

RUN apt-get update && apt-get install -y findutils && apt-get install dos2unix tzdata

# 타임존을 한국으로 설정
RUN ln -sf /usr/share/zoneinfo/Asia/Seoul /etc/localtime && echo "Asia/Seoul" > /etc/timezone

# 작업 디렉토리 설정
WORKDIR /app

# 프로젝트 root에서 Gradle Wrapper와 설정 파일 복사
COPY backend/gradlew /app/
COPY backend/gradle /app/gradle
RUN dos2unix /app/gradlew && chmod +x /app/gradlew

# 프로젝트 설정 파일 복사
COPY backend/settings.gradle /app/
COPY backend/build.gradle /app/
COPY backend/core /app/core
COPY backend/member /app/member

# member 모듈의 빌드 실행
WORKDIR /app/member
RUN /app/gradlew bootJar --no-daemon

# 최종 이미지
FROM openjdk:17-jdk-buster
# 타임존을 한국으로 설정 (최종 이미지에서도)
RUN ln -sf /usr/share/zoneinfo/Asia/Seoul /etc/localtime && echo "Asia/Seoul" > /etc/timezone

COPY --from=builder /app/member/build/libs/*.jar /home/server.jar
EXPOSE 8107
ENTRYPOINT ["java", "-jar", "/home/server.jar", "--spring.profiles.active=dev"]
