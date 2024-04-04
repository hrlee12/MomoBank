import { defineStore } from "pinia";

export const useUserStore = defineStore({
  id: "userStore",
  state: () => ({
    messageVerifyAuthToken: "",
    userPhoneNumber: "",
  }),
  actions: {
    setAuthToken(token) {
      this.messageVerifyAuthToken = token;
    },
    setPhoneNumber(number) {
      this.userPhoneNumber = number;
    },
  },
});
