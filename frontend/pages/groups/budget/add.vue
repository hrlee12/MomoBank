<script setup>
import GroupsBottomSheetModal from "~/components/layout/GroupsBottomSheetModal.vue";

definePageMeta({
  layout: "no-footer-bank",
});

const getImageUrl = (imageName, idx) => {
  // Note: You might need to adjust the path depending on your project structure
  if (idx == 0) return "/icon/" + imageName;
  else if (idx == 1) return "/images/" + imageName;
  else console.log("Image code error");
};

const budgetGoal = ref("");
const targetAmount = ref("");

// 이체주기 선택
const visibleFrequency = ref(false);

const visibleBottomModal = ref(false);

const frequencyDay = ref(null);

const visibleBottomModalClick = () => {
  visibleBottomModal.value = true;
  visibleFrequency.value = true;
};

const handleUpdate = (event) => {
  visibleBottomModal.value = event.isVisible;
  visibleFrequency.value = event.budgetAddVisible;
  frequencyDay.value = event.frequencyDay.value;
};

// TODO: 확인 버튼 클릭시 저장 API 호출
</script>
<template>
  <div class="relative">
    <div class="absolute right-5 top-[-2.4rem] font-bold text-main-color">
      확인
    </div>
  </div>
  <div class="h-screen bg-white">
    <div class="px-4 py-2">
      <div class="py-3">
        <div class="py-3 text-xl font-bold">어떤 목적의 예산인가요?</div>
        <div class="border-b-2 border-main-color">
          <input
            type="text"
            v-model="budgetGoal"
            placeholder="예산 목적 입력"
            class="w-full text-lg"
          />
        </div>
      </div>
      <div class="py-3">
        <div class="py-3 text-xl font-bold">목표 금액은 얼마인가요?</div>
        <div class="border-b-2 border-main-color">
          <input
            type="text"
            v-model="targetAmount"
            placeholder="목표 금액 입력"
            class="w-full text-lg"
          />
        </div>
      </div>
      <div class="py-3 text-xl font-bold">언제까지 모아야 하나요?</div>
      <div>
        <input type="date" class="border-none" name="goalInput" />
      </div>
      <div class="py-3 text-xl font-bold">며칟날 입금할 건가요?</div>
      <div
        v-if="frequencyDay === null || frequencyDay === 'null'"
        class="flex items-center"
      >
        <div @click="visibleBottomModalClick">이체주기 선택</div>
        <div class="w-5 h-5 rotate-180">
          <img :src="getImageUrl('arrow-icon.png', 0)" alt="arrow-icon" />
        </div>
      </div>
      <div
        v-if="
          frequencyDay !== null &&
          frequencyDay !== '말일' &&
          frequencyDay !== 'null'
        "
        @click="visibleBottomModalClick"
      >
        매월 {{ frequencyDay }}일
      </div>
      <div
        v-if="
          frequencyDay !== null &&
          frequencyDay === '말일' &&
          frequencyDay !== 'null'
        "
        @click="visibleBottomModalClick"
      >
        매월 {{ frequencyDay }}
      </div>
    </div>
    <GroupsBottomSheetModal
      :isFrequency="visibleFrequency"
      :isVisible="visibleBottomModal"
      @budget-add-update="handleUpdate"
    ></GroupsBottomSheetModal>
    <div v-if="visibleBottomModal" class="modal-bg"></div>
  </div>
</template>
<style lang="scss" scoped>
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
