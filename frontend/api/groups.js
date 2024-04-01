// api/bank.js
import { useNuxtApp } from "#app";

export function useGroupApi() {
  const { $axios } = useNuxtApp();

  const getGroupFeedList = async (groupId) => {
    return await $axios.get(
      `/community/feeds?page=0&size=20&groupId=${groupId}`
    );
  };

  const getGroupHome = async (groupId, memberId) => {
    return await $axios.get(`/user/groups/${groupId}?memberId=${memberId}`);
  };

  const getGroupDetail = async (groupId, memberId) => {
    return await $axios.get(
      `/user/groups/${groupId}/detail?memberId=${memberId}`
    );
  };

  const getMyGroups = async (memberId) => {
    return await $axios.get(`/user/groups/my-groups?memberId=${memberId}`);
  };

  const getGroupMembers = async (groupId) => {
    return await $axios.get(`/user/groups/${groupId}/members`);
  };

  // 모든 함수를 반환하여 외부에서 사용할 수 있게 함
  return {
    getGroupFeedList,
    getGroupHome,
    getGroupDetail,
    getMyGroups,
    getGroupMembers,
  };
}
