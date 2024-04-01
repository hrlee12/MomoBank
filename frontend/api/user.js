// api/bank.js
import { useNuxtApp } from "#app";

export function useUserApi() {
  const { $axios } = useNuxtApp();

  const loginRequest = async (data) => {
    return await $axios.get(`/user/member/login`, data);
  };

  // 모든 함수를 반환하여 외부에서 사용할 수 있게 함
  return { loginRequest };
}
