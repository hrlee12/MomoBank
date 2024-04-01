// api/bank.js
import { useNuxtApp } from "#app";

export function useUserApi() {
  const { $axios } = useNuxtApp();

  const loginRequest = async (data, success, fail) => {
    return await $axios
      .post(`/user/auth/login`, data)
      .then(success)
      .catch(fail);
  };

  // 모든 함수를 반환하여 외부에서 사용할 수 있게 함
  return { loginRequest };
}
