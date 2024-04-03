// api/bank.js
import { useNuxtApp } from "#app";

export function useUserApi() {
  const { $axios } = useNuxtApp();

  // 로그인 요청
  const loginRequest = async (data, success, fail) => {
    return await $axios
      .post(`/user/auth/login`, data)
      .then(success)
      .catch(fail);
  };

  // 휴대폰 인증 메세지 요청
  const requestVerifyMessage = async (data, success, fail) => {
    return await $axios
      .post(`/user/auth/phone-verification/code`, data)
      .then(success)
      .catch(fail);
  };

  // 휴대폰 인증 메세지 확인
  const confirmVerifyMessage = async (data, success, fail) => {
    return await $axios
      .post(`/user/auth/phone-verification/verify`, data)
      .then(success)
      .catch(fail);
  };

  // 회원가입 요청
  const userJoinRequest = async (data, success, fail) => {
    return await $axios.post(`/user/auth/join`, data).then(success).catch(fail);
  };

  // 마이페이지 요청
  const getMyPageData = async (memberId) => {
    return await $axios.get(`user/member/${memberId}`);
  };

  // 모든 함수를 반환하여 외부에서 사용할 수 있게 함
  return {
    loginRequest,
    requestVerifyMessage,
    confirmVerifyMessage,
    userJoinRequest,
    getMyPageData,
  };
}
