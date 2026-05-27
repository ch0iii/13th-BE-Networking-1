
# 배포 문서

## 1. 아키텍처 다이어그램
``` 
[사용자]
  ↓ HTTPS 요청
  [13.125.211.86.nip.io]
  ↓
[Nginx (80/443)]
  ↓ 리버스 프록시
[Spring Boot 컨테이너 (8080)]
  ↓
[MySQL 컨테이너 (3306)]
 [GitHub Actions]
  ↓ main/feat 브랜치 push
[Docker 이미지 빌드 (linux/amd64)]
  ↓
[Docker Hub (saon0410/cotato-app)]
  ↓ pull
[EC2 서버]
```

## 2. 배포 URL

- 배포 URL: `https://13.125.211.86.nip.io`
- Swagger URL: `https://13.125.211.86.nip.io/swagger-ui/index.html`

## 3. 배포된 Swagger 접속 화면

<img width="700" height="600" alt="스크린샷 2026-05-27 오후 3 10 07" src="https://github.com/user-attachments/assets/73c40977-12ee-454b-9e73-917efc3d91de" />

## 4. GitHub Actions 화면

<img width="700" height="550" alt="스크린샷 2026-05-27 오후 3 47 54" src="https://github.com/user-attachments/assets/2350048b-f19d-4ce1-9f31-f6d037117a6d" />

## 5. Dockerfile / Nginx 설정

### Dockerfile

```dockerfile
# ---- Build Stage ----
FROM eclipse-temurin:17-jdk AS builder

WORKDIR /app

COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .

RUN chmod +x ./gradlew
RUN ./gradlew dependencies --no-daemon

COPY src src
RUN ./gradlew bootJar -x test --no-daemon

# ---- Run Stage ----
FROM eclipse-temurin:17-jre

WORKDIR /app

RUN groupadd appgroup && useradd -g appgroup appuser

COPY --from=builder /app/build/libs/*.jar app.jar

RUN chown appuser:appgroup app.jar
USER appuser

EXPOSE 8080

ENTRYPOINT ["java", \
  "-Djava.security.egd=file:/dev/./urandom", \
  "-jar", "app.jar"]
```

### Nginx 설정

```nginx
server {
    listen 80;
    server_name 13.125.211.86.nip.io;

    location / {
        proxy_pass http://127.0.0.1:8080;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;
    }
}
```

## 6. 트러블슈팅 노트

### 문제 1. M1 Mac 이미지 아키텍처 불일치
**문제:** EC2에서 컨테이너 실행 시 `exec format error` 발생  
**원인:** M1/M2 Mac(ARM)에서 빌드한 이미지를 x86 기반 EC2에서 실행 불가  
**해결:** `docker buildx build --platform linux/amd64` 옵션으로 재빌드 후 전송

### 문제 2. 3306 포트 충돌
**문제:** `docker compose up` 시 포트 이미 사용 중 에러 발생  
**원인:** 로컬 MySQL이 이미 3306 포트를 점유 중  
**해결:** `docker-compose.yml`에서 호스트 포트를 3307로 변경

### 문제 3. ddl-auto validate 오류
**문제:** 컨테이너 최초 실행 시 `missing table [application]` 에러 발생  
**원인:** `application-prod.yml`의 `ddl-auto: validate` 설정으로 인해 테이블이 없으면 실행 불가  
**해결:** `ddl-auto: update`로 변경하여 최초 실행 시 테이블 자동 생성

### 문제 4. GitHub Actions 네트워크 이름 불일치
**문제:** EC2 배포 시 `network 13th-be-networking-1_default not found` 에러  
**원인:** EC2의 실제 Docker 네트워크 이름이 `ubuntu_default`였음  
**해결:** `deploy.yml`의 네트워크 이름을 `ubuntu_default`로 수정

### 문제 5. t2.micro 메모리 부족으로 SSH 연결 끊김
**문제:** EC2에서 `sudo apt install` 실행 중 SSH 연결이 반복적으로 끊김  
**원인:** t2.micro의 메모리가 1GB로 Docker 컨테이너 실행 중 패키지 설치 시 메모리 부족 발생  
**해결:** Swap 메모리 1GB 추가로 여유 확보

```bash
sudo fallocate -l 1G /swapfile
sudo chmod 600 /swapfile
sudo mkswap /swapfile
sudo swapon /swapfile
echo '/swapfile none swap sw 0 0' | sudo tee -a /etc/fstab
```

### 문제 6. apt 패키지 설치 무한 대기
**문제:** `sudo apt install -y nginx` 실행 후 `Reading package lists... 0%` 에서 10분 이상 진행되지 않음  
**원인:** 메모리 부족으로 인해 EC2 자체가 응답 불능 상태  
**해결:** AWS 콘솔에서 EC2 인스턴스 재부팅 후 Swap 메모리 추가한 뒤 재설치 성공
## 7. 배포 방식

로컬에서 `docker save` → `scp`로 EC2에 전송하는 방식으로 초기 배포를 진행했습니다.
이후 GitHub Actions를 통해 자동 배포를 구성했습니다.

### GitHub Actions 배포 흐름
1. `feat/choiii` 브랜치에 push
2. GitHub Actions 실행
3. `linux/amd64` 플랫폼으로 Docker 이미지 빌드
4. Docker Hub(`saon0410/cotato-app`)에 이미지 push
5. EC2에 SSH 접속
6. 새 이미지 pull 후 컨테이너 교체
