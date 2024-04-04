<script setup>
import Loading from "~/components/layout/Loading.vue";

// 2초 후에 /bank/ 페이지로 라우팅
setTimeout(() => {
  navigateTo("/user/");
}, 2000);
</script>

<template>
  <Loading />
</template>

<style lang="scss">
/* 수평 스크롤 바 감추기 */
body::-webkit-scrollbar {
  display: none;
}

/* 수직 스크롤 바 감추기 */
body::-webkit-scrollbar-thumb {
  display: none;
}
</style>
