<script setup>
import { useNuxtApp } from "#app";
import { useBankApi } from "@/api/bank";

const { remitBalance } = useBankApi();

// Nuxt 앱 인스턴스에서 $router를 가져옵니다.
const { $router } = useNuxtApp();

// 스토어 상태에 접근
const remitStore = useRemitStore();
const remitInfo = remitStore.remitInfo;

const formattedMoney = remitInfo.remitAmount.toLocaleString("ko-KR"); // 숫자를 한국어 통화 형식으로 변환
const menuIdx = ref(0); // 메뉴 인덱스 (0 - 확인 페이지, 1 - 송금 성공 페이지, 2 - 송금 실패 페이지)

// 이미지 불러오는 메소드
const getImageUrl = (imageName, idx) => {
  if (idx == 0) return "/icon/" + imageName;
  else if (idx === 1) return "/images/" + imageName;
  else console.log("Image code error");
};

const goNext = () => {
  if (menuIdx.value == 0) remit();
  else $router.push("/bank/");
};

const remit = async () => {
  await remitBalance(
    // myAccountId, targetAccountId, remitAmount
    {
      fromAccountId: remitInfo.myAccountId,
      toAccountId: remitInfo.targetAccountId,
      amount: remitInfo.remitAmount,
    },
    (data) => {
      menuIdx.value = 1;
    },
    (error) => {
      menuIdx.value = 2;
      console.log(error);
    }
  );
};

definePageMeta({
  layout: "action",
});
</script>

<template>
  <div class="confirm-container">
    <!-- <BankRemitConfirm /> -->
    <div v-if="menuIdx == 0" class="confirm-content">
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

    <!-- 로딩 -->
    <!--성공 -->
    <div v-if="menuIdx == 1" class="confirm-content">
      <img :src="getImageUrl('check-icon.png', 0)" alt="" />
      <div class="confirm-item">
        <h1 class="emphasize">{{ remitInfo.targetAccountBankName }}</h1>
        <h2>으로</h2>
      </div>

      <div class="confirm-item">
        <h1>{{ formattedMoney }}원</h1>
        <h2>을</h2>
      </div>
      <h2 class="confirm-item">송금했어요</h2>
    </div>

    <!-- 실패 -->
    <div v-if="menuIdx == 2" class="confirm-content">
      <img :src="getImageUrl('error-icon.png', 0)" alt="" />
      <div class="confirm-item">
        <h1>송금 실패</h1>
      </div>

      <div class="confirm-content">
        <p>계좌의 잔액이 부족해요.</p>
        <p>잔액이 충분한 계좌로 바꿔주세요.</p>
      </div>
    </div>
    <!-- 다음 버튼 -->
    <div class="btn-container">
      <button class="prime-btn" @click="goNext">다음</button>
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
  align-items: center;
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

img {
  height: 7vh;
}
</style>
