<script setup>
definePageMeta({
  layout: "action",
});

function formatWithHyphens(str) {
  return str.replace(/(\d{3})(\d{2})(\d{5})/, "$1-$2-$3");
}

const handleUpdate = (eventPayload) => {
  // 올바른 계좌 정보인지 확인하는 API
  // [[ bankName으로 바꿔라 ]]
  // { accountId, eventPayload.bankInfo.value.bankId, eventPayload.targetAccountNumber.value }
  // -> { myAccountName, myAccountBalance, targetAccountId, targetUserName }
  // 인증이 성공적이라면 데이터 업데이트 (메뉴 인덱스 변경 및 목표 계좌번호 및 은행사 정보)
  // 인증이 실패라면 통과
  targetAccountInfo.value.accountNumber = formatWithHyphens(
    eventPayload.targetAccountNumber.value
  );
};
</script>

<template>
  <div class="input-container">
    <BankRemitAccountInfo @update:menuIndex="handleUpdate" />
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
