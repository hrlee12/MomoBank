<script setup>
import SimpleAccount from "~/components/bank/SimpleAccount.vue";
import AddBox from "~/components/bank/AddBox.vue";

definePageMeta({
  layout: "bank",
});

const accounts = ref([
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
</script>

<template>
  <div class="account-container">
    <div v-for="(account, index) in accounts" :key="index">
      <SimpleAccount
        :accountName="account.accountName"
        :accountNumber="account.accountNumber"
        :money="account.money"
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
