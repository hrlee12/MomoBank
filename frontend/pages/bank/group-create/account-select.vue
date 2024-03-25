<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
import SimpleAccount from "~/components/bank/SimpleAccount.vue";

definePageMeta({
  layout: "action",
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

const isSelected = ref(false);
const selectedId = ref();
const selectAccount = (index) => {
  selectedId.value = index;
  isSelected.value = true;
  // console.log(selectedId.value + " " + isSelected.value);
};

const router = useRouter();

const goNext = () => {
  router.push(`/`);
};
</script>

<template>
  <div class="account-container">
    <div
      v-for="(account, index) in accounts"
      :key="index"
      @click="selectAccount(index)"
      class="account"
      :class="{ 'selected-account': selectedId == index }"
    >
      <SimpleAccount
        :accountName="account.accountName"
        :accountNumber="account.accountNumber"
        :money="account.money"
        :status="false"
      />
    </div>
    <button v-if="!isSelected" class="second-btn">다음</button>
    <button v-else class="prime-btn" @click="goNext()">다음</button>
  </div>
</template>

<style lang="scss" scoped>
@import "~/assets/css/main.scss";
@import "~/assets/css/action.scss";

.account-container {
  padding: 5%;
  display: grid;
  grid-template-rows: repeat(auto, 1fr);
  gap: 30px;
}
.account {
  border-radius: 20px;
  border-color: none;
}

.selected-account {
  box-shadow: 3px 3px 10px -3px $primary-color;
  outline: 3px solid $primary-color;
  transition-duration: 0.2s;
}

.prime-btn,
.second-btn {
  width: 100% !important;
  border-radius: 15px !important;
}
</style>
