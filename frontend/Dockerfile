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

# 프로덕션을 위한 패키지 설치(tailwind 오류 남)
# RUN npm ci --only=production

# 문서화 목적 포트
EXPOSE 3000

# Nuxt.js 앱 실행
CMD ["node", "./.output/server/index.mjs"]

