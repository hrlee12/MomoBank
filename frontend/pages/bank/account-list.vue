<script setup>
import SimpleAccount from "~/components/bank/SimpleAccount.vue";
import AddBox from "~/components/bank/AddBox.vue";
import { useBankApi } from "@/api/bank";

const { getMyAccountList } = useBankApi();

definePageMeta({
  layout: "bank",
});

const myAccountList = ref([]);
// { accountName: "저축은행", accountNumber: "123-1234-12345", money: 1000000 },

// 스토어 상태에 접근
const remitStore = useRemitStore();

// 전체 계좌 리스트 받는 함수
onMounted(async () => {
  try {
    const memberId = remitStore.memberId;
    const response = await getMyAccountList(memberId);
    myAccountList.value = response.data.data.myAccountList;
    console.log(myAccountList);
    console.log(myAccountList.value);
  } catch (error) {
    console.error(error);
  }
});
</script>

<template>
  <div class="account-container">
    <div
      v-if="myAccountList === undefined || myAccountList.length === 0"
      class="center"
    >
      계좌가 존재하지 않습니다.
    </div>
    <div v-else v-for="(account, index) in myAccountList" :key="index">
      <SimpleAccount
        :accountName="account.accountProductName"
        :accountNumber="account.accountNumber"
        :money="account.balance"
        :status="true"
      />
    </div>
    <AddBox to="/bank/account-create" />
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
