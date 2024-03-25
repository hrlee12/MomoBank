<script setup>
import { ref } from "vue";

definePageMeta({
  layout: "bank",
});

const noticeList = ref([
  { type: 0, title: "회비요청", date: "24-02-12" },
  { accountName: "효리은행", accountNumber: "123-1234-12346", money: 2000000 },
  { accountName: "성수은행", accountNumber: "123-1234-12347", money: 1000 },
  { accountName: "소이은행", accountNumber: "123-1234-12348", money: 100000 },
  { accountName: "준성은행", accountNumber: "123-1234-12349", money: 10000 },
]);
</script>

<template>
  <div class="account-container">
    <div v-for="(notice, index) in noticeList" :key="index">
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
</style>
