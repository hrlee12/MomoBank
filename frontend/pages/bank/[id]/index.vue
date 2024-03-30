<script setup>
import BankAccount from "~/components/bank/BankAccount.vue";
import AccountHistory from "~/components/bank/AccountHistory.vue";

const { id } = useRoute().params; // 가로안에 들어가는 변수 명은 해당 []안에 들어간 이름과 통일
definePageMeta({
  layout: "action",
});

// 받아온 id로 account-detail과 get-transfer api호출

// account-detail temp result
const accountInfo = ref({
  accountId: 0,
  accountType: "입출금",
  accountName: "저축은행",
  accountNumber: "123-1234-12345",
  balance: 1000000,
});

// get-transfer temp result
const totalTransferList = ref([
  {
    transferList: [
      {
        name: "엄세현",
        date: "13:24", // 시 분
        ammount: 1315678,
        transferType: false, // true 송금 받은거 false 송금 한거
        balance: 1000000,
      },
      {
        name: "엄세현",
        date: "13:12", // 시 분
        ammount: 3000000,
        transferType: false, // true 송금 받은거 false 송금 한거
        balance: 2315678,
      },
    ],
    date: "02-19", // 일자
  },
  {
    transferList: [
      {
        name: "엄세현",
        date: "20:24", // 시 분
        ammount: 30000,
        transferType: true, // true 송금 받은거 false 송금 한거
        balance: 5615678,
      },
      {
        name: "엄세현",
        date: "15:24", // 시 분
        ammount: 30000,
        transferType: true, // true 송금 받은거 false 송금 한거
        balance: 5585678,
      },
      {
        name: "엄세현",
        date: "12:24", // 시 분
        ammount: 30000,
        transferType: true, // true 송금 받은거 false 송금 한거
        balance: 5555678,
      },
    ],
    date: "02-18", // 일자
  },
  {
    transferList: [
      {
        name: "엄세현",
        date: "13:24", // 시 분
        ammount: 1315678,
        transferType: false, // true 송금 받은거 false 송금 한거
        balance: 1000000,
      },
      {
        name: "엄세현",
        date: "13:12", // 시 분
        ammount: 3000000,
        transferType: false, // true 송금 받은거 false 송금 한거
        balance: 2315678,
      },
    ],
    date: "02-19", // 일자
  },
  {
    transferList: [
      {
        name: "엄세현",
        date: "20:24", // 시 분
        ammount: 30000,
        transferType: true, // true 송금 받은거 false 송금 한거
        balance: 5615678,
      },
      {
        name: "엄세현",
        date: "15:24", // 시 분
        ammount: 30000,
        transferType: true, // true 송금 받은거 false 송금 한거
        balance: 5585678,
      },
      {
        name: "엄세현",
        date: "12:24", // 시 분
        ammount: 30000,
        transferType: true, // true 송금 받은거 false 송금 한거
        balance: 5555678,
      },
    ],
    date: "02-18", // 일자
  },
]);
</script>

<template>
  <div class="account-container">
    <div class="account-content">
      <BankAccount :accountInfo="accountInfo" />
    </div>

    <div class="history-container">
      <AccountHistory
        v-for="(transferList, index) in totalTransferList"
        :key="index"
        :transferInfoPerDate="transferList"
      />
    </div>
  </div>
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
}
</style>
