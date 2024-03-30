<script setup>
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
const selectFrequency = ref(false);

const selectFrequencyClick = () => {
  selectFrequency.value = true;
};
</script>
<template>
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
        <input type="date" class="border-none" />
      </div>
      <div class="py-3 text-xl font-bold">며칟날 입금할 건가요?</div>
      <div
        v-if="!selectFrequency"
        class="flex items-center"
        @click="selectFrequencyClick"
      >
        <div>이체주기 선택</div>
        <div class="w-5 h-5 rotate-180">
          <img :src="getImageUrl('arrow-icon.png', 0)" alt="arrow-icon" />
        </div>
      </div>
      <div v-if="selectFrequency">
        
      </div>
    </div>
  </div>
</template>
