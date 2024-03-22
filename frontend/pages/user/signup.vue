<script setup>
import { ref } from "vue";

const inputNumber = ref("");

// 버튼 클릭 또는 엔터 키 이벤트 처리를 위한 함수
const handleInput = () => {
  console.log(inputNumber.value); // 콘솔에 입력 값을 출력
  // 이곳에서 inputNumber.value를 사용하여 추가 작업을 수행할 수 있습니다.
};

function oninputPhone(target) {
  target.value = target.value
    .replace(/[^0-9]/g, "")
    .replace(/(^02.{0}|^01.{1}|[0-9]{3,4})([0-9]{3,4})([0-9]{4})/g, "$1-$2-$3");
}

definePageMeta({
  layout: "user",
});
</script>

<template>
  <div class="login-container">
    <h3>모모의 다양한 서비스를 이용하기 위해 본인 확인이 필요합니다.</h3>
    <input
      type="number"
      placeholder="전화번호 ex) 01012341234"
      v-model="inputNumber"
      @keyup.enter="handleInput"
    />
    <button class="second-btn" @click="handleInput">인증 요청</button>
  </div>
</template>

<style lang="scss" scoped>
@import "~/assets/css/main.scss";
@import "~/assets/css/user.scss";

.login-container {
  width: 90%;
  height: 30vh;
  display: flex;
  flex-direction: column;
  justify-content: space-evenly;
  text-align: center;
}
</style>
