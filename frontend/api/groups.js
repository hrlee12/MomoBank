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

  const getGroupNoticeList = async (groupId) => {
    return await $axios.get(`/community/notice/list/${groupId}`);
  };

  const postGroupNotice = async (notice) => {
    return await $axios.post(`/community/notice/create`, notice);
  };

  const getGroupBudget = async (groupId, memberId) => {
    return await $axios.get(
      `/user/groups/${groupId}/budgets/budgets-list?memberId=${memberId}`
    );
  };

  const postGroupBudget = async (groupId, budget) => {
    return await $axios.post(
      `/user/groups/${groupId}/budgets/new-budget`,
      budget
    );
  };

  const patchGroupBudget = async (groupId, budgetId, budget) => {
    return await $axios.post(
      `/user/groups/${groupId}/budgets/${budgetId}`,
      budget
    );
  };

  const deleteGroupBudget = async (groupId, budgetId) => {
    return await $axios.delete(`/user/groups/${groupId}/budgets/${budgetId}`);
  };

  const getTransactionHistory = async (memberId, accountId) => {
    return await $axios.get(
      `/user/bank/get-transfer?memberId=${memberId}&accountId=${accountId}`
    );
  };

  const getMyFeed = async (groupMemberId) => {
    return await $axios.get(
      `/community/feeds/group/myfeed?groupMemberId=${groupMemberId}`
    );
  };

  const createNewGroup = async (data, success, fail) => {
    console.log("in axios");
    return await $axios
      .post(`/user/groups/new-group`, data)
      .then(success)
      .catch(fail);
  };

  const createInviteCode = async (groupId) => {
    return await $axios.post(`/user/groups/${groupId}/invite`);
  };

  const createInviteAuthCode = async (inviteCode, memberId) => {
    return await $axios.post(
      `/user/groups/invite/${inviteCode}
      `,
      { memberId: memberId }
    );
  };

  const groupJoinWithInviteCode = async (data, success, fail) => {
    console.log("inAXIOS: ", data);
    return await $axios
      .post(`/user/groups/invite/accounts`, data)
      .then(success)
      .catch(fail);
  };

  // 모든 함수를 반환하여 외부에서 사용할 수 있게 함
  return {
    getGroupFeedList,
    getGroupHome,
    getGroupDetail,
    getMyGroups,
    getGroupMembers,
    getGroupNoticeList,
    postGroupNotice,
    getGroupBudget,
    postGroupBudget,
    patchGroupBudget,
    deleteGroupBudget,
    getTransactionHistory,
    getMyFeed,
    createNewGroup,
    createInviteCode,
    createInviteAuthCode,
    groupJoinWithInviteCode,
  };
}
