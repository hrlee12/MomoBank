# 단계 1: Node.js 환경에서 Nuxt.js 앱 빌드
FROM node:20.11.1 AS build-stage

WORKDIR /app

# 프로젝트 의존성 파일 복사
COPY package*.json ./

# 프로젝트 의존성 설치
RUN npm ci

# 애플리케이션 소스 코드 복사
COPY . .

# Nuxt.js 애플리케이션 빌드
RUN npm run build

# 단계 2: Nginx를 사용해 앱 서빙
FROM nginx:stable-alpine AS production-stage

WORKDIR /usr/share/nginx/html

# Nuxt.js 빌드 결과물을 Nginx 서빙 디렉토리로 복사
COPY --from=build-stage /app/.output/public .

EXPOSE 3000

CMD ["nginx", "-g", "daemon off;"]
