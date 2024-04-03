<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
import SimpleAccount from "~/components/bank/SimpleAccount.vue";
import Loading from "~/components/layout/Loading.vue";

definePageMeta({
  layout: "action",
});

// 이미지 불러오는 메소드
const getImageUrl = (imageName, idx) => {
  if (idx == 0) return "/icon/" + imageName;
  else if (idx === 1) return "/images/" + imageName;
  else console.log("Image code error");
};

const accounts = ref([
  // { accountName: "저축은행", accountNumber: "123-1234-12345", money: 1000000 },
]);

const isSelected = ref(false);
const selectedId = ref();
const isLoading = ref(false); // 로딩 상태 관리
const method = ref(-1);

const selectAccount = (index) => {
  selectedId.value = index;
  isSelected.value = true;
  // console.log(selectedId.value + " " + isSelected.value);
};

const router = useRouter();

// 비동기 데이터 로딩을 시뮬레이션하는 함수
const loadData = async () => {
  isLoading.value = true; // 로딩 시작
  try {
    // 데이터 로딩 로직 (여기서는 setTimeout을 사용하여 시뮬레이션)
    await new Promise((resolve) => setTimeout(resolve, 5000)); // 5초 대기
    // 데이터 로딩 완료
    isLoading.value = false; // 로딩 완료
    router.push("/bank/group-create/card-select"); // 로딩 완료 후 card-select 페이지로 이동
  } catch (error) {
    console.error("데이터 로딩 중 오류 발생:", error);
    isLoading.value = false; // 에러 시 로딩 상태 해제
  }
};

const goCreateBankAccount = () => {
  router.push("/bank/account-create");
};

const makeGroup = () => {
  // group 생성 api 호출
};
</script>

<template>
  <div v-if="method == -1" class="account-container">
    <div class="method-container">
      <div class="method-content" @click="method = 0">
        <img :src="getImageUrl('card-icon.png', 0)" alt="" />
        <h3>기존 계좌 연결</h3>
      </div>
      <div class="method-content" @click="goCreateBankAccount">
        <img :src="getImageUrl('logo-icon.png', 0)" alt="" />
        <h3>신규 계좌 생성</h3>
      </div>
    </div>
  </div>
  <div v-else-if="method == 0" class="account-container">
    <div
      v-for="(account, index) in accounts"
      :key="index"
      @click="selectAccount(index)"
      class="account"
      :class="{ selected: selectedId == index }"
    >
      <SimpleAccount
        :accountName="account.accountName"
        :accountNumber="account.accountNumber"
        :money="account.money"
        :status="false"
      />
    </div>
    <button v-if="!isSelected" class="second-btn">다음</button>
    <button v-else class="prime-btn" @click="makeGroup">다음</button>
  </div>
</template>

<style lang="scss" scoped>
@import "~/assets/css/main.scss";
@import "~/assets/css/action.scss";

.method-container {
  padding-top: 5vh;
}

.account-container {
  padding: 3% 0 7% 0;
  display: grid;
  grid-template-rows: repeat(auto, 1fr);
  gap: 20px;
  height: 100%;
  width: 90%;
}
.account {
  border-radius: 20px;
  border-color: none;
}

.prime-btn,
.second-btn {
  width: 100% !important;
  border-radius: 15px !important;
}
</style>
