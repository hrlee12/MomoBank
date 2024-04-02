// api/bank.js
import { useNuxtApp } from "#app";

export function useBankApi() {
  const { $axios } = useNuxtApp();

  // 사용자 전체 계좌 조회
  const getMyAccountList = async (memberId) => {
    return await $axios.get(`/user/bank/my-accounts/${memberId}`);
  };

  // 송금
  const remitBalance = async (data) => {
    return await $axios.post("/user/bank/transfer", data);
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

  const deleteLike = async (cardId) => {
    return await $axios.delete(`/flashcard/${cardId}/favorite`);
  };

  // 모든 함수를 반환하여 외부에서 사용할 수 있게 함
  return {
    getMyAccountList,
    remitBalance,
    getAccountDetail,
    getTransactionHistory,
  };
}
