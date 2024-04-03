<script setup>
import KebabMenu from "@/components/ui/KebabMenu";
import { ref } from "vue";
import { useNuxtApp } from "#app";
import { useBankApi } from "~/api/bank";

const { $router } = useNuxtApp(); // Nuxt 앱 인스턴스에서 $router를 가져옵니다.
const { deleteBankAccount } = useBankApi(); // bank api 함수 가져오기
const remitStore = useRemitStore(); // 스토어 상태에 접근
const historyMenuActive = ref(true); // 거래 내역 페이지 확인 변수

const { accountInfo } = defineProps({
  accountInfo: Object,
});

// 이미지 불러오는 메소드
const getImageUrl = (imageName, idx) => {
  if (idx == 0) return "/icon/" + imageName;
  else if (idx === 1) return "/images/" + imageName;
  else console.log("Image code error");
};

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

const goNext = (param) => {
  console.log("before go to remit: ", accountInfo);
  remitStore.setMyAccountInfo(
    accountInfo.accountId,
    accountInfo.accountProductName,
    accountInfo.accountProductType,
    accountInfo.accountNumber,
    accountInfo.balance
  );

  if (param == 0) {
    remitStore.remitInfo.myAccountId = accountInfo.accountId;
    $router.push(`/bank/account-detail`);
  } else if (param == 1) $router.push("/bank/remit");
};

const handleAccountDeletion = async () => {
  // 계좌 삭제 요청 처리
};
</script>

<template>
  <div v-if="accountInfo" class="content account-content">
    <div class="account-item">
      <div class="account-info">
        <h2>{{ accountInfo.accountProductName }}</h2>
        <div class="account-no">
          <p @click="copyTextToClipboard(accountInfo.accountNumber)">
            {{ accountInfo.accountProductType }} {{ accountInfo.accountNumber }}
          </p>
        </div>
      </div>
      <KebabMenu @deleteAccount="handleAccountDeletion" />
    </div>
    <div class="money-content">
      <h1 v-if="!hide && accountInfo.balance != undefined">
        {{ accountInfo.balance.toLocaleString("ko-KR") }}원
      </h1>
      <h1 v-else-if="!hide">{{ accountInfo.balance }}원</h1>
      <h1 v-else-if="hide">잔액 숨김 중</h1>

      <button @click="hideActive()">
        <p v-if="!hide">숨김</p>
        <p v-else-if="hide">보기</p>
      </button>
    </div>

    <div class="link-content">
      <div v-if="historyMenuActive" @click="goNext(0)" class="account-menu">
        거래내역
      </div>
      <div @click="goNext(1)" class="account-menu">송금하기</div>
    </div>
  </div>
  <div v-else class="content account-content account-menu">
    <NuxtLink to="/bank/account-create"
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

    .account-menu + .account-menu {
      border-left: 1px solid $light-gray-color;
    }
  }
}
.account-menu {
  color: $primary-color;
  font-weight: bold;
  text-align: center;
  width: 50%;
  border-radius: 0 m !important;
  font-size: 2.2vh;

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
