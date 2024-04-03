import { defineStore } from "pinia";

export const useRemitStore = defineStore({
  id: "remitStore",
  state: () => ({
    memberId: -1, // 로그인한 사용자 고유 인덱스 아이디
    memberLoginId: "사용자 로그인 아이디",
    memberName: "사용자 이름",
    createBankAccountProductId: 0, // 계좌 개설 시 선택한 계좌 제품 아이디
    remitInfo: {
      myAccountId: 0, // 내 계좌 고유 인덱스 아이디
      myAccountName: "내 계좌명",
      myAccountBalance: 0, // 내 계좌 금액
      myAccountType: "내 계좌 종류", // (입출금)
      myAccountNumber: "내 계좌 번호",
      targetAccountId: 0, // 송금 계좌 고유 인덱스 아이디
      targetAccountNumber: "송금 계좌 번호",
      targetAccountUserName: "송금 계좌 소유자명",
      targetAccountBankId: 0, // 송금 계좌 은행사 고유 인덱스 아이디
      targetAccountBankName: "송금계좌 은행사 이름",
      targetAccountBankLogoUrl: "송금 계좌 은행사 로고 이미지 URL",
      remitAmount: 0, //송금할 금액
    },
    surveyData: { selections: {} },
  }),
  actions: {
    setMyAccountInfo(id, name, type, number, balance) {
      this.remitInfo.myAccountId = id;
      this.remitInfo.myAccountName = name;
      this.remitInfo.myAccountType = type;
      this.remitInfo.myAccountNumber = number;
      this.remitInfo.myAccountBalance = balance;
    },

    updateMyAccountId(id) {
      console.log("I'm in pinia" + id);
      this.remitInfo.myAccountId = id;
    },
  },
});
