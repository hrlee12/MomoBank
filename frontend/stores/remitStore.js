import { defineStore } from "pinia";

export const useRemitStore = defineStore({
  id: "remitStore",
  state: () => ({
    remitInfo: {
      myAccountId: 0, // 내 계좌 고유 인덱스 아이디
      myAccountName: "내 계좌명", // 내 계좌 명
      myAccountBalance: 0, // 내 계좌 금액
      targetAccountId: 0, // 송금 계좌 고유 인덱스 아이디
      targetAccountNumber: "송금 계좌 번호", // 송금 계좌 번호
      targetAccountUserName: "송금 계좌 소유자명", // 송금 계좌 소유자 명
      targetAccountBankId: 0, // 송금 계좌 은행사 고유 인덱스 아이디
      targetAccountBankName: "송금계좌 은행사 이름", // 송금 계좌 은행사 이름
      targetAccountBankLogoUrl: "String", // 송금 계좌 은행사 로고 이미지 URL
      remitAmount: 0, //송금할 금액
    },
  }),
  actions: {
    setMyAccountInfo(id, name, balance) {
      this.remitInfo.myAccountId = id;
      this.remitInfo.myAccountName = name;
      this.remitInfo.myAccountBalance = balance;
    },

    updateMyAccountId(id) {
      console.log("I'm in pinia" + id);
      this.remitInfo.myAccountId = id;
    },
  },
});
