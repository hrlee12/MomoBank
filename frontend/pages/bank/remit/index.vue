<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";

defineProps({
  accountId: Number, // 내 계좌 아이디
});

definePageMeta({
  layout: "action",
});

const menuIndex = ref(0); // 메뉴 인덱스 변수
const myAccountInfo = ref({
  name: String,
  balance: Number,
}); // 내 계좌 정보 (내 계좌 이름, 계좌 잔액)
const targetAccountInfo = ref({
  id: Number,
  accountNumber: String,
  userName: String,
}); // 송금 목표 정보 (계좌 아이디, 계좌번호, 계좌 소유자 이름)
const targetBankInfo = ref({
  bankId: Number,
  bankName: String,
  bankLogoUrl: String,
}); // 목표 계좌 은행 정보

const router = useRouter();

function formatWithHyphens(str) {
  return str.replace(/(\d{3})(\d{2})(\d{5})/, "$1-$2-$3");
}

const handleUpdate = (eventPayload) => {
  // 올바른 계좌 정보인지 확인하는 API
  // { accountId, eventPayload.bankInfo.value.bankId, eventPayload.targetAccountNumber.value }
  // -> { myAccountName, myAccountBalance, targetAccountId, targetUserName }
  // 인증이 성공적이라면 데이터 업데이트 (메뉴 인덱스 변경 및 목표 계좌번호 및 은행사 정보)
  // 인증이 실패라면 통과
  menuIndex.value = eventPayload.menuIndex;
  targetAccountInfo.value.accountNumber = formatWithHyphens(
    eventPayload.targetAccountNumber.value
  );
  targetBankInfo.value = eventPayload.bankInfo.value;

  myAccountInfo.value.name = "저축은행";
  myAccountInfo.value.balance = 1000000;
  targetAccountInfo.value.id = 10;
  targetAccountInfo.value.userName = "명소이";

  // 로그 확인
  console.log(targetAccountInfo.value.accountNumber);
  console.log(targetBankInfo.value);
};
</script>

<template>
  <div class="input-container">
    <BankRemitAccountInfo
      v-if="menuIndex == 0"
      @update:menuIndex="handleUpdate"
    />
    <BankRemitMoneyInput
      v-if="menuIndex == 1"
      :myAccountInfo="myAccountInfo"
      :targetAccountInfo="targetAccountInfo"
      :targetBankInfo="targetBankInfo"
    />
  </div>
</template>

<style lang="scss" scoped>
@import "~/assets/css/main.scss";
@import "~/assets/css/action.scss";

.input-container {
  height: 90%;
  width: 95%;
}
</style>
