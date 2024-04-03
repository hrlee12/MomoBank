<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";

definePageMeta({
  layout: "action",
});

const router = useRouter();
const surveyList = ref([
  {
    question: "어떤 카드사를 사용하고싶으신가요? (다중 선택 가능)",
    options: [
      "kb국민카드",
      "삼성카드",
      "롯데카드",
      "BC 바로카드",
      "신한카드",
      "현대카드",
      "우리카드",
      "NH농협카드",
      "하나카드",
      "IBK기업은행",
      "선호 없음",
    ],
  },
  {
    question: "어떤 혜택을 받고싶으신가요? (다중 선택 가능)",
    options: [
      "통신+공과금 혜택",
      "주유+차량정비 혜택",
      "쇼핑 혜택",
      "항공마일리지 혜택",
      "점심+교통 혜택",
      "무실적+모든가맹점 혜택",
      "구독/스트리밍 혜택",
      "해외직구 혜택",
      "배달앱+간편결제 혜택",
      "편의점+카페 혜택",
      "마트+교육비 혜택",
      "여행+바우처 혜택",
      "제휴/PLCC 혜택",
      "증권사CMA 혜택",
      "선호 없음",
    ],
  },
  {
    question: "어떤 형태의 혜택을 받고싶으신가요? (다중 선택 가능)",
    options: ["할인형", "포인트형", "마일리지형", "선호 없음"],
  },
  {
    question: "월간 사용량은 어느 정도인가요? (한 개만 선택)",
    options: ["예측 불가능", "30만원 이하", "30만원 이상"],
  },
]);
</script>

<template>
  <div class="survey-container">
    <div
      v-for="(survey, surveyIndex) in surveyList"
      :key="surveyIndex"
      class="survey-content"
    >
      <h3>{{ surveyIndex + 1 }}. {{ survey.question }}</h3>
      <div
        v-for="(option, optionIndex) in survey.options"
        :key="optionIndex"
        class="survey-item"
      >
        <input
          type="checkbox"
          :checked="selectedOptions[surveyIndex]?.includes(optionIndex)"
          @click="toggleOption(surveyIndex, optionIndex)"
        />
        <p>{{ option }}</p>
      </div>
    </div>
  </div>
</template>

<style lang="scss" scoped>
@import "~/assets/css/main.scss";

.survey-container {
  padding: 0 5%;
}

.survey-content {
  padding: 3% 0;
}

.survey-item {
  display: flex;
  padding: 1% 0;

  input {
    margin: 0 2%;
  }
}
</style>
