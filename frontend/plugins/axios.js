// plugins/axios.js
import axios from "axios";
import { defineNuxtPlugin } from "#app";

export default defineNuxtPlugin((nuxtApp) => {
  const instance = axios.create({
    baseURL: "https://j10a505.p.ssafy.io/api/",
    timeout: 5000,
    headers: {
      "Content-Type": "application/json",
    },
  });

  instance.interceptors.request.use(
    (config) => {
      // 요청 전 처리 로직
      return config;
    },
    (error) => {
      return Promise.reject(error);
    }
  );

  instance.interceptors.response.use(
    (response) => {
      // 응답 받은 후 처리 로직
      return response;
    },
    (error) => {
      return Promise.reject(error);
    }
  );

  // axios 인스턴스를 nuxtApp의 모든 컴포넌트에서 `$axios`로 사용할 수 있도록 설정
  nuxtApp.provide("axios", instance);
});
