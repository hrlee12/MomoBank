<script setup>
definePageMeta({
  layout: "action",
});

import { ref } from "vue";
import { useNuxtApp } from "#app";

// Nuxt 앱 인스턴스에서 $router를 가져옵니다.
const { $router } = useNuxtApp();

// 스토어 상태에 접근
const remitStore = useRemitStore();
const remitInfo = remitStore.remitInfo;

const moneyInput = ref();

const goNext = () => {
  remitInfo.remitAmount = moneyInput.value;
  $router.push("/bank/remit/password-check");
};
</script>

<template>
  <div class="input-container">
    <div class="input-content">
      <div class="input-item">
        <h1>{{ remitInfo.myAccountName }}에서</h1>
        <p>잔액 {{ remitInfo.myAccountBalance.toLocaleString("ko-KR") }}원</p>
      </div>
      <div class="input-item">
        <h1>{{ remitInfo.targetAccountUserName }} 계좌로</h1>
        <p>
          {{ remitInfo.targetAccountBankName }}
          {{ remitInfo.targetAccountNumber }}
        </p>
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
      <button v-else class="prime-btn" @click="goNext">다음</button>
    </div>
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

.input-container {
  height: 90%;
  width: 95%;
}
</style>
