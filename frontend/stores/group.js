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
  },
});
