<script setup>
import { onMounted, onUnmounted, ref } from "vue";
import { navigateTo } from "#app"; // navigateTo 함수를 import

// 이미지 불러오는 메소드
const getImageUrl = (imageName, idx) => {
  if (idx == 0) return "/icon/" + imageName;
  else if (idx === 1) return "/images/" + imageName;
  else console.log("Image code error");
};

const timeoutId = ref(null); // setTimeout의 ID를 저장할 ref

const angle = ref(0); // 회전 각도를 저장하는 ref

function rotateAndPause() {
  angle.value += 90; // 각도를 90도 증가시키고
  // 이미지에 회전 적용
  document.querySelector(
    ".rotate-image"
  ).style.transform = `rotate(${angle.value}deg)`;

  // 회전 후 1초 대기하고 다시 rotateAndPause 함수 호출
  timeoutId.value = setTimeout(() => {
    rotateAndPause(); // 재귀 호출
  }, 1000); // 90도 회전 후 1초 대기 (총 대기 시간을 4초로 하여 5초마다 반복)
}

onMounted(() => {
  rotateAndPause(); // 컴포넌트가 마운트되면 애니메이션 시작

  // 2초 후에 /bank/ 페이지로 라우팅
  setTimeout(() => {
    navigateTo("/bank/");
  }, 2000);
});

onUnmounted(() => {
  if (timeoutId.value) {
    clearTimeout(timeoutId.value); // 컴포넌트가 언마운트되면 타이머 취소
  }
});
</script>

<template>
  <div class="loading-content">
    <img :src="getImageUrl('logo-icon.png', 0)" alt="" class="rotate-image" />

    <div class="text-content">
      <h2>모두가 모이는 뱅킹</h2>
      <h1>모모뱅크</h1>
    </div>
  </div>
</template>

<style lang="scss" scoped>
@import "@/assets/css/main.scss";

.loading-content {
  display: flex;
  flex-direction: column;
  align-items: center;
  height: 90vh;
  margin-top: 30vh;
}

.rotate-image {
  height: 15vh;
  margin-bottom: 10%;
  transition: transform 1s ease; /* 회전에 걸리는 시간을 4초로 설정 */
}

.text-content {
  text-align: center;
  padding: 5%;
}
</style>
