<script setup>
import SimpleAccount from "~/components/bank/SimpleAccount.vue";
import AddBox from "~/components/bank/AddBox.vue";
import { getAccountList } from "~/api/bank.js";

definePageMeta({
  layout: "bank",
});

const getMyAccountList = () => {
  // axios함수를 통해 데이터 불러오기
  getAccountList(
    ({ data }) => {
      accountList.value = data.accountList;
    },
    (error) => {
      console.log(error);
    }
  );
};

const accountList = ref([
  { accountName: "저축은행", accountNumber: "123-1234-12345", money: 1000000 },
  { accountName: "효리은행", accountNumber: "123-1234-12346", money: 2000000 },
  { accountName: "성수은행", accountNumber: "123-1234-12347", money: 1000 },
  { accountName: "소이은행", accountNumber: "123-1234-12348", money: 100000 },
  { accountName: "준성은행", accountNumber: "123-1234-12349", money: 10000 },
  {
    accountName: "민우은행",
    accountNumber: "123-1234-12349",
    money: 100000000,
  },
]);

onMounted(() => {
  getMyAccountList();
});
</script>

<template>
  <div class="account-container">
    <div v-for="(account, index) in accountList" :key="index">
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
</style>
