<script setup>
import BankBottomSheetModal from "~/components/layout/BankBottomSheetModal.vue";
import { useNuxtApp } from "#app";
import { useBankApi } from "~/api/bank";
const { getTargetAccountInfo } = useBankApi();

// Nuxt 앱 인스턴스에서 $router를 가져옵니다.
const { $router } = useNuxtApp();

// 스토어 상태에 접근
const remitStore = useRemitStore();
const remitInfo = remitStore.remitInfo;

// 이미지 불러오는 메소드
const getImageUrl = (imageName, idx) => {
  if (idx == 0) return "/icon/" + imageName;
  else if (idx === 1) return "/images/" + imageName;
  else console.log("Image code error");
};

const isModalVisible = ref(false); // 모달창 상태 변수
const targetAccount = ref({
  id: Number,
  number: "",
  userName: "",
}); // 전송할 목표 계좌 번호

const bankInfo = ref({
  id: Number,
  name: "",
}); // 목표 계좌 은행 정보

// emit 받아오기 (isVisible, bankInfo)
const handleUpdate = (eventPayload) => {
  console.log("선택한 은행사 정보: ", eventPayload.bankInfo);
  isModalVisible.value = eventPayload.isVisible;
  if (eventPayload.bankInfo != "") {
    bankInfo.value.id = eventPayload.bankInfo.bankId;
    bankInfo.value.name = eventPayload.bankInfo.companyName;
  }
};

// 송금 대상이 존재하는지 확인
const checkTarget = async () => {
  await getTargetAccountInfo(
    {
      myAccountId: remitStore.memberId,
      bankName: bankInfo.value.name,
      accountNumber: targetAccount.value.number,
    },
    (data) => {
      targetAccount.value.id = data.data.data.to.accountId;
      targetAccount.value.userName = data.data.data.to.name;
      console.log("송금 대상 요청이 존재합니다: ", data.data);

      // 얻어낸 정보 pinia에 저장
      remitInfo.targetAccountBankId = bankInfo.value.id;
      remitInfo.targetAccountId = targetAccount.value.id;
      remitInfo.targetAccountNumber = targetAccount.value.number;
      remitInfo.targetAccountUserName = targetAccount.value.userName;
      remitInfo.targetAccountBankName = bankInfo.value.name;

      // 페이지 이동
      $router.push("/bank/remit/money-input");
    },
    (error) => {
      console.log("송금할 수 있는 계좌가 아닙니다: ", error);
      alert("송금할 수 있는 계좌가 아닙니다.");
    }
  );
};
</script>

<template>
  <div class="input-content">
    <h1>어떤 계좌로 보낼까요?</h1>
    <!-- 계좌번호 입력란 -->
    <div class="input-item">
      <input
        type="text"
        v-model="targetAccount.number"
        placeholder="계좌번호"
      />
    </div>
    <!-- 은행사 선택란 -->
    <div class="input-item" @click="isModalVisible = true">
      <input
        type="text"
        v-model="bankInfo.name"
        placeholder="은행 선택"
        readonly
        class="bank-input"
      />
      <img :src="getImageUrl('arrow-icon.png', 0)" alt="" />
    </div>
  </div>
  <!-- 다음 버튼 -->
  <div class="btn-container">
    <button
      v-if="targetAccount.number == '' || bankInfo.name == ''"
      class="second-btn"
    >
      다음
    </button>
    <button v-else class="prime-btn" @click="checkTarget()">다음</button>
  </div>
  <!-- 모달 -->
  <BankBottomSheetModal :isVisible="isModalVisible" @update="handleUpdate" />
  <div v-if="isModalVisible" class="modal-bg"></div>
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

.input-item {
  display: flex;
}

img {
  height: 4vh;
  position: absolute;
  right: 7%;
  rotate: -90deg;
}

//----------------
.modal-bg {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.6);
  z-index: 999;
}
</style>
