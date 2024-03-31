import { defineStore } from "pinia";

export const useRemitStore = defineStore({
  id: "remitStore",
  state: () => ({
    remitInfo: {
      myAccountId: Number, // 내 계좌 고유 인덱스 아이디
      myAccountName: String, // 내 계좌 명
      myAccountBalance: Number, // 내 계좌 금액
      targetAccountId: Number, // 송금 계좌 고유 인덱스 아이디
      targetAccountNumber: String, // 송금 계좌 번호
      targetAccountUserName: String, // 송금 계좌 소유자 명
      targetAccountBankId: Number, // 송금 계좌 은행사 고유 인덱스 아이디
      targetAccountBankName: String, // 송금 계좌 은행사 이름
      targetAccountBankLogoUrl: String, // 송금 계좌 은행사 로고 이미지 URL
      remitAmount: Number, //송금할 금액
    },
  }),
  actions: {
    updateMenuTitle(title) {
      this.menuTitle = title;
    },

    updateSideMenuActive(active) {
      this.sideMenuActive = active;
    },
  },
});
