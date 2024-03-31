// plugins/axios.js
import axios from "axios";

export const localAxios = () => {
  const instance = axios.create({
    baseURL: "https://j10a505.p.ssafy.io/", // 기본 URL 설정
    timeout: 5000, // 요청 시간 초과
    headers: {
      "Content-Type": "application/json",
      // 필요한 경우 추가 헤더 설정
    },
  });

  // 요청 인터셉터 설정
  instance.interceptors.request.use(
    (config) => {
      // 요청 전 처리 로직
      return config;
    },
    (error) => {
      return Promise.reject(error);
    }
  );

  // 응답 인터셉터 설정
  instance.interceptors.response.use(
    (response) => {
      // 응답 받은 후 처리 로직
      return response;
    },
    (error) => {
      return Promise.reject(error);
    }
  );

  return instance;
};
