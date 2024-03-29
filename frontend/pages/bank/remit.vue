<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
import BottomSheetModal from "~/components/layout/BottomSheetModal.vue";

defineProps({
  accountId: Number,
});

definePageMeta({
  layout: "action",
});

const accountNumber = ref();
const bankName = ref("");
const menuIndex = ref(0);
const router = useRouter();
const isModalVisible = ref(false);

const goNext = () => {
  menuIndex.value += 1;
};
</script>

<template>
  <div class="input-container">
    <div class="input-content">
      <h1>어떤 계좌로 보낼까요?</h1>
      <div class="input-item">
        <input type="text" v-model="accountNumber" placeholder="계좌번호" />
      </div>
      <div class="input-item" @click="isModalVisible = true">
        <input
          type="text"
          v-model="bankName"
          placeholder="은행 선택"
          readonly
        />
      </div>
    </div>
    <div class="btn-container">
      <button v-if="accountNumber == '' || bankName == ''" class="second-btn">
        다음
      </button>
      <button v-else class="prime-btn" @click="goNext()">다음</button>
    </div>
  </div>
  <BottomSheetModal
    :isVisible="isModalVisible"
    @update:isVisible="isModalVisible = $event"
  />
  <div v-if="isModalVisible" class="modal-bg"></div>
</template>

<style lang="scss" scoped>
@import "~/assets/css/main.scss";
@import "~/assets/css/action.scss";

.prime-btn,
.second-btn {
  border-radius: 15px !important;
}

.input-container {
  height: 90%;
  width: 95%;
}

.input-content {
  padding-top: 5vh;
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
