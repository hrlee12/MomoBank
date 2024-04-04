// api/bank.js
import { useNuxtApp } from "#app";

export function useBankApi() {
  const { $axios } = useNuxtApp();

  // 사용자 전체 계좌 조회
  const getMyAccountList = async (memberId) => {
    return await $axios.get(`/user/bank/my-accounts/${memberId}`);
  };

  // 송금
  const remitBalance = async (data, success, fail) => {
    return await $axios
      .post("/user/bank/transfer", data)
      .then(success)
      .catch(fail);
  };

  // 송금할 계좌 정보 조회
  const getTargetAccountInfo = async (data, success, fail) => {
    return await $axios
      .post("/user/bank/account-search", data)
      .then(success)
      .catch(fail);
  };

  // 계좌 상세 정보
  const getAccountDetail = async (memberId, accountId) => {
    return await $axios.get(
      `/user/bank/account-detail?memberId=${memberId}&accountId=${accountId}`
    );
  };

  // 거래내역 조회
  const getTransactionHistory = async (memberId, accountId) => {
    return await $axios.get(
      `/user/bank/get-transfer?memberId=${memberId}&accountId=${accountId}`
    );
  };

  // 은행사 리스트 조회
  const getBankList = async () => {
    return await $axios.get(`/user/bank/bank-list`);
  };

  // 은행사 생성 가능한 계좌 종류 조회
  const getBankAccountTypeList = async () => {
    return await $axios.get(`user/bank/account-products`);
  };

  // 은행사 계좌 생성
  const createBankAccount = (data, success, fail) => {
    return $axios
      .post(`user/bank/create-account`, data)
      .then(success)
      .catch(fail);
  };

  // 계좌 비밀번호 확인
  const confirmAccountPassword = (data, success, fail) => {
    return $axios
      .post(`user/bank/password-confirm`, data)
      .then(success)
      .catch(fail);
  };

  // 카드 추천 리스트 조회
  const getCardRecommendList = async (data, success, fail) => {
    JSON.stringify(data);
    return await $axios
      .post(`community/recommend/recommend-card`, data)
      .then(success)
      .catch(fail);
  };

  const deleteBankAccount = (data) => {
    return $axios.delete(`/user/bank/delete-account`, data);
  };

  // 모든 함수를 반환하여 외부에서 사용할 수 있게 함
  return {
    getMyAccountList,
    remitBalance,
    getAccountDetail,
    getTransactionHistory,
    getBankList,
    getTargetAccountInfo,
    getBankAccountTypeList,
    createBankAccount,
    confirmAccountPassword,
    getCardRecommendList,
    deleteBankAccount,
  };
}
