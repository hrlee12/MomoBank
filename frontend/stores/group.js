// stores/group.js
import { defineStore } from "pinia";

export const useGroupStore = defineStore("group", {
  state: () => ({
    link: "https://j10a505.p.ssafy.io/",
    groupHeaderName: "",
    accountNumber: "",
    groupId: undefined,
    paymentStatus: undefined,
    groupMemberId: undefined,
    accountId: 5,
    groupBalance: null,
    groupRole: null,
    createGroupName: "",
    createGroupDesc: "",
    inviteCodeGroupId: null,
    inviteStatus: false,
  }),
  actions: {
    updateGroupHeaderName(newName) {
      this.groupHeaderName = newName;
    },
    updateAccountNumber(newAccountNumber) {
      this.accountNumber = newAccountNumber;
    },
    updateGroupId(newGroupId) {
      this.groupId = newGroupId;
    },
    updatePaymentStatus(newPaymentStatus) {
      this.paymentStatus = newPaymentStatus;
    },
    updateGroupMemberId(newGroupMemberId) {
      this.groupMemberId = newGroupMemberId;
    },
    updateGroupBalance(newGroupBalance) {
      this.groupBalance = newGroupBalance;
    },
    updateGroupRole(newGroupRole) {
      this.groupRole = newGroupRole;
    },
    createGroupName(newGroupName) {
      this.createGroupName = newGroupName;
    },
    createGroupDesc(newGroupDesc) {
      this.createGroupDesc = newGroupDesc;
    },
    updateInviteGroupId(newInviteGroupId) {
      this.inviteCodeGroupId = newInviteGroupId;
    },
    updateInviteStatus(newInviteStatus) {
      this.inviteStatus = newInviteStatus;
    },
  },
});
