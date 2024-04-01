// api/bank.js
import { useNuxtApp } from "#app";

export function useBankApi() {
  const { $axios } = useNuxtApp();

  const getMyAccountList = async (memberId) => {
    return await $axios.get(`/user/bank/my-accounts/${memberId}`);
  };

  const deleteLike = async (cardId) => {
    return await $axios.delete(`/flashcard/${cardId}/favorite`);
  };

  const postLike = async (cardId) => {
    return await $axios.post(`/flashcard/${cardId}/favorite`);
  };

  const sendCardID = async (data) => {
    return await $axios.patch(`/flashcard/weight`, data);
  };

  // 모든 함수를 반환하여 외부에서 사용할 수 있게 함
  return { getMyAccountList };
}
