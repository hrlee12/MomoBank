<script setup>
import SimpleAccount from "~/components/bank/SimpleAccount.vue";
import AddBox from "~/components/bank/AddBox.vue";
import { useBankApi } from "@/api/bank";

const { getMyAccountList } = useBankApi();

definePageMeta({
  layout: "bank",
});

const accountList = ref([]);
// { accountName: "저축은행", accountNumber: "123-1234-12345", money: 1000000 },
//   { accountName: "효리은행", accountNumber: "123-1234-12346", money: 2000000 },
//   { accountName: "성수은행", accountNumber: "123-1234-12347", money: 1000 },
//   { accountName: "소이은행", accountNumber: "123-1234-12348", money: 100000 },
//   { accountName: "준성은행", accountNumber: "123-1234-12349", money: 10000 },
//   {
//     accountName: "민우은행",
//     accountNumber: "123-1234-12349",
//     money: 100000000,
//   },

// 전체 계좌 리스트 받는 함수
onMounted(async () => {
  try {
    const memberId = 13; // 예시 ID
    const response = await getMyAccountList(memberId);
    accountList.value = response.data;
    console.log(accountList.value);
  } catch (error) {
    console.error(error);
  }
});
</script>

<template>
  <div class="account-container">
    <div v-if="accountList.length === undefined" class="center">
      계좌가 존재하지 않습니다.
    </div>
    <div v-else v-for="(account, index) in accountList" :key="index">
      <SimpleAccount
        :accountName="account.accountName"
        :accountNumber="account.accountNumber"
        :money="account.money"
        :status="true"
      />
    </div>
    <AddBox to="/bank/remit" />
  </div>
</template>

<style lang="scss" scoped>
@import "~/assets/css/main.scss";

.account-container {
  padding: 5%;
  display: grid;
  grid-template-rows: repeat(auto, 1fr);
  gap: 30px;
}

.center {
  text-align: center;
}
</style>
