<script setup>
import { ref } from "vue";

import { useRouter } from "vue-router"; // useRouter 추가

const inputNumber = ref("");
const router = useRouter(); // useRouter 인스턴스 생성

// 입력값이 숫자인지 확인하는 함수
const validateNumberInput = (event) => {
  const value = event.target.value;
  // 숫자와 맨 앞 0을 허용하는 정규식
  if (!value.match(/^[0-9]*$/)) {
    // 숫자가 아닌 문자가 입력되면 마지막 문자 제거
    inputNumber.value = value.slice(0, -1);
  } else {
    // 숫자만 입력된 경우, inputNumber 업데이트
    inputNumber.value = value;
  }
};

// 엔터키 이벤트 핸들러
const handleEnter = () => {
  if (inputNumber.value.length == 11) router.push(`/user/${inputNumber.value}`);
  // 입력값을 경로의 일부로 사용하여 페이지 이동
  else alert("전화번호를 정확히 입력해주십시오.");
};

definePageMeta({
  layout: "user",
});
</script>

<template>
  <div class="login-container">
    <h3>모모의 다양한 서비스를 이용하기 위해 본인 확인이 필요합니다.</h3>
    <input
      type="text"
      placeholder="전화번호 ex) 01012341234"
      v-model="inputNumber"
      @keyup.enter="handleEnter"
      @input="validateNumberInput"
    />

    <button class="second-btn" @click="handleEnter">인증요청</button>
  </div>
</template>

<style lang="scss" scoped>
@import "~/assets/css/main.scss";
@import "~/assets/css/user.scss";
</style>
