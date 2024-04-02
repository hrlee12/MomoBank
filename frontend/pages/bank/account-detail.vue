<script setup>
import BankAccount from "~/components/bank/BankAccount.vue";
import AccountHistory from "~/components/bank/AccountHistory.vue";
import BankFooter from "~/components/layout/BankFooter.vue";

definePageMeta({
  layout: "action",
});

// get-transfer temp result
const transactionHistory = ref([
  // {
  //   transferList: [
  //     {
  //       name: "엄세현",
  //       date: "13:24", // 시 분
  //       ammount: 1315678,
  //       transferType: false, // true 송금 받은거 false 송금 한거
  //       balance: 1000000,
  //     },
  //     {
  //       name: "엄세현",
  //       date: "13:12", // 시 분
  //       ammount: 3000000,
  //       transferType: false, // true 송금 받은거 false 송금 한거
  //       balance: 2315678,
  //     },
  //   ],
  //   date: "02-19", // 일자
  // },
]);

// 스토어 상태에 접근
const remitStore = useRemitStore();
const remitInfo = remitStore.remitInfo;
// account-detail temp result
const accountInfo = ref({
  accountId: remitInfo.myAccountId,
  accountProductType: remitInfo.myAccountType,
  accountProductName: remitInfo.myAccountName,
  accountNumber: remitInfo.myAccountNumber,
  balance: remitInfo.myAccountBalance,
});

// 받아온 id로 account-detail과 get-transfer api호출
import { useBankApi } from "~/api/bank";
const { getAccountDetail, getTransactionHistory } = useBankApi();

// 계좌 상세 정보 요청 함수
const requestAccountDetail = async () => {
  try {
    const memberId = remitStore.memberId;
    const accountId = remitStore.remitInfo.myAccountId;
    const response = await getAccountDetail(memberId, accountId);
    accountInfo.value = response.data.data;
    console.log("accountInfo: ", accountInfo.value);
  } catch (error) {
    console.error("계좌 상세 정보 요청 실패:", error);
  }
};

// 거래내역 조회 요청 함수
const requestTransactionHistory = async () => {
  try {
    const memberId = remitStore.memberId;
    const accountId = remitStore.remitInfo.myAccountId;
    const response = await getTransactionHistory(memberId, accountId);
    transactionHistory.value = response.data.data.totalTransferList;
    console.log("transactionHistory: ", transactionHistory.value);
  } catch (error) {
    console.error("거래내역 조회 요청 실패:", error);
  }
};

// 컴포넌트 마운트 시 API 요청 실행
onMounted(async () => {
  await requestAccountDetail();
  await requestTransactionHistory();
});
</script>

<template>
  <div class="account-container">
    <div class="account-content">
      <BankAccount :accountInfo="accountInfo" />
    </div>

    <div
      v-if="transactionHistory.length == 0 || transactionHistory == undefined"
      class="history-container center"
    >
      조회된 기록이 없습니다.
    </div>
    <div v-else class="history-container">
      <AccountHistory
        v-for="(history, index) in transactionHistory"
        :key="index"
        :history="history"
      />
    </div>
  </div>
  <BankFooter />
</template>

<style lang="scss" scoped>
@import "~/assets/css/main.scss";
@import "~/assets/css/content.scss";
* {
  background-color: $background-color;
}
.account-container {
  width: 100%;
}
.account-content {
  border-radius: 20px;
}
.content {
  box-shadow: none;
  height: 25vh;
  border: none !important;
  border-top-left-radius: 0;
  border-top-right-radius: 0;
}

.history-container {
  margin: 2% 0;
  background-color: white;
  border-radius: 15px;
  padding: 3vh 5vw 10vh 5vw;
  min-height: 60vh;
}

.center {
  text-align: center;
}
</style>
