<script setup>
import KebabMenu from "@/components/ui/KebabMenu";
import { useRoute } from "#vue-router";
import { ref } from "vue";

const router = useRoute();

const historyMenuActive = ref(true);
const id = router.params.id;
if (id && router.name.startsWith("bank-")) {
  historyMenuActive.value = false;
}

defineProps({
  accountInfo: Object,
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
  <div v-if="accountInfo" class="content account-content">
    <div class="account-item">
      <div class="account-info">
        <h2>{{ accountInfo.accountName }}</h2>
        <div class="account-no">
          <p @click="copyTextToClipboard(accountInfo.accountNumber)">
            {{ accountInfo.accountType }} {{ accountInfo.accountNumber }}
          </p>
        </div>
      </div>
      <!-- <KebabMenu /> -->
      <KebabMenu />
    </div>
    <div class="money-content">
      <h1 v-if="!hide">{{ accountInfo.balance.toLocaleString("ko-KR") }}원</h1>
      <h1 v-if="hide">잔액 숨김 중</h1>

      <button @click="hideActive()">
        <p v-if="!hide">숨김</p>
        <p v-if="hide">보기</p>
      </button>
    </div>

    <div class="link-content">
      <NuxtLink :to="`/bank/${accountInfo.accountId}`" v-if="historyMenuActive"
        >거래내역</NuxtLink
      >
      <NuxtLink to="/bank/remit" :accountId="accountInfo.accountId"
        >송금하기</NuxtLink
      >
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
  min-height: 100px;
  padding: 2vh 3vw 2vh 3vw;

  height: 25vh;

  transition: transform 0.5s ease-in-out;

  .account-item {
    display: flex;
    justify-content: space-between;
    width: 100%;
    padding: 0 5%;

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
    width: 100%;
    align-self: center;
    padding-top: 2vh;

    a + a {
      border-left: 1px solid $light-gray-color;
    }
  }
}
a {
  color: $primary-color;
  font-weight: bold;
  text-align: center;
  width: 50%;

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
