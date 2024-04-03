<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";

definePageMeta({
  layout: "action",
});

const router = useRouter();
const serveyList = ref([
  {
    question: "어떤 카드사를 사용하고싶으신가요? (다중 선택 가능)",
    optionList: [
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
]);
</script>

<template>
  <div>survey</div>
</template>

<style lang="scss" scoped></style>
