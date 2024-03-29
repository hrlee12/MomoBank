<script setup>
import { ref } from "vue";
defineProps({
  myAccountInfo: Object, // 내 계좌 정보
  targetAccountInfo: Object, // 목표 계좌 정보
  targetBankInfo: Object, // 목표 은행 정보
});

const moneyInput = ref();
</script>

<template>
  <div class="input-content">
    <div class="input-item">
      <h1>{{ myAccountInfo.name }}에서</h1>
      <p>잔액 {{ myAccountInfo.balance.toLocaleString("ko-KR") }}원</p>
    </div>
    <div class="input-item">
      <h1>{{ targetAccountInfo.userName }}계좌로</h1>
      <p>{{ targetBankInfo.name }} {{ targetAccountInfo.accountNumber }}</p>
    </div>
    <div class="input-item">
      <!-- 계좌번호 입력란 -->
      <input
        type="number"
        v-model="moneyInput"
        placeholder="얼마나 보낼까요?"
      />
      <h1 v-if="moneyInput">{{ moneyInput.toLocaleString("ko-KR") }}원</h1>
    </div>
  </div>

  <!-- 다음 버튼 -->
  <div class="btn-container">
    <button v-if="moneyInput == null" class="second-btn">다음</button>
    <button v-else class="prime-btn">
      <NuxtLink
        :to="{
          path: '/bank/remit/confirm-remit',
          query: {
            myAccountName: myAccountInfo.name,
            money: moneyInput,
          },
        }"
        >다음</NuxtLink
      >
    </button>
  </div>
</template>

<style lang="scss" scoped>
@import "~/assets/css/main.scss";
@import "~/assets/css/action.scss";

.prime-btn,
.second-btn {
  border-radius: 15px !important;
}

.input-content {
  padding-top: 5vh;
}
</style>
