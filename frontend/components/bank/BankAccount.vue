<script setup>
import KebabMenu from "@/components/ui/KebabMenu";
defineProps({
  accountName: String,
  accountNumber: String,
  money: Number,
});

// 이미지 불러오는 메소드
const getImageUrl = (imageName, idx) => {
  if (idx == 0) return "/icon/" + imageName;
  else if (idx === 1) return "/images/" + imageName;
  else console.log("Image code error");
};

const textToCopy = ref("");
const copyTextToClipboard = async (text) => {
  try {
    await navigator.clipboard.writeText(text);
    alert(`클립보드에 복사되었습니다! text-${text}`);
  } catch (err) {
    console.error("클립보드 복사 실패:", err);
  }
};

const hide = ref(false);
const hideActive = () => {
  hide.value = !hide.value;
};
</script>

<template>
  <div v-if="accountName" class="content account-content">
    <div class="account-item">
      <div class="account-info">
        <h2>{{ accountName }}</h2>
        <div class="account-no">
          <p @click="copyTextToClipboard(accountNumber)">
            입출금 {{ accountNumber }}
          </p>
        </div>
      </div>
      <!-- <KebabMenu /> -->
      <KebabMenu />
    </div>
    <div class="money-content">
      <h1 v-if="!hide">{{ money.toLocaleString("ko-KR") }}원</h1>
      <h2 v-if="hide">잔액 숨김 중</h2>

      <button @click="hideActive()">
        <p v-if="!hide">숨김</p>
        <p v-if="hide">보기</p>
      </button>
    </div>

    <div class="link-content">
      <NuxtLink to="/bank/history">거래내역</NuxtLink>
      <NuxtLink to="/bank/remit">송금하기</NuxtLink>
    </div>
  </div>
  <div v-else class="content account-content">
    <NuxtLink to="/bank/remit"
      ><div><img :src="getImageUrl('add-icon.png', 0)" alt="" /></div>
      <h1>계좌 개설</h1></NuxtLink
    >
  </div>
</template>

<style lang="scss" scoped>
@import "@/assets/css/main.scss";

.account-content {
  min-height: 200px;
  padding: 2vh 3vw 2vh 3vw;
  border: 1px solid #a3a3a3 !important;

  height: 25vh;

  transition: transform 0.5s ease-in-out;

  .account-item {
    display: flex;
    justify-content: space-between;
    width: 100%;
    padding: 0 5% 0 5%;
    text-align: left;

    .account-no {
      display: flex;
      color: $gray-color;
    }
  }

  .money-content {
    display: flex;
    justify-content: center;
    align-self: center;
    width: 100%;

    h1 {
      margin-right: 2%;
    }

    button {
      background-color: $light-gray-color;
      border-radius: 20px;
      height: 90%;
      min-width: 50px;
      color: $gray-color;
    }

    p {
      font-size: 1.8vh;
    }
  }

  .link-content {
    display: flex;
    justify-content: space-around;
    border-top: 1px solid $light-gray-color;
    width: 90%;
    align-self: center;
    padding-top: 2vh;
  }
}
a {
  color: $primary-color;
  font-weight: bold;
  text-align: center;

  div {
    margin-top: 6vh;
    display: flex;
    justify-content: center;
  }
  img {
    height: 6vh;
    filter: invert(26%) sepia(59%) saturate(7496%) hue-rotate(206deg)
      brightness(96%) contrast(101%);
  }
}
</style>
