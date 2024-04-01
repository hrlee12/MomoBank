// stores/group.js
import { defineStore } from "pinia";

export const useGroupStore = defineStore("group", {
  state: () => ({
    groupHeaderName: "",
    accountNumber: "",
    groupId: undefined,
    paymentStatus: undefined,
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
  },
});
