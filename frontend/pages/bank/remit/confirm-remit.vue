<script setup>
import { useRoute } from "vue-router";

const remitStore = useRemitStore();

// 스토어 상태에 접근
const remitInfo = remitStore.remitInfo;

definePageMeta({
  layout: "action",
});

const route = useRoute();

const formattedMoney = remitInfo.remitAmount.toLocaleString("ko-KR"); // 숫자를 한국어 통화 형식으로 변환
</script>

<template>
  <div class="confirm-container">
    <div class="confirm-content">
      <div class="confirm-item">
        <h1 class="emphasize">{{ remitInfo.targetAccountBankName }}</h1>
        <h2>으로</h2>
      </div>

      <div class="confirm-item">
        <h1>{{ formattedMoney }}원</h1>
        <h2>을</h2>
      </div>
      <h2 class="confirm-item">송금할까요?</h2>
    </div>

    <!-- 다음 버튼 -->
    <div class="btn-container">
      <button class="prime-btn">다음</button>
    </div>
  </div>
</template>

<style lang="scss" scoped>
@import "~/assets/css/main.scss";
@import "~/assets/css/action.scss";

.confirm-container {
  padding: 5vh 0;
  width: 100%;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  align-items: center;
}

.confirm-content {
  display: flex;
  flex-direction: column;
  justify-content: center;
}

.confirm-item {
  display: flex;
  margin: 5% 0;
  justify-content: center;
}

h1 {
  font-size: 4vh;
}
h2 {
  font-size: 3vh;
}
</style>
~/stores/remit-store
