<script setup>
import Loading from "~/components/layout/Loading.vue";

// 2초 후에 /bank/ 페이지로 라우팅
setTimeout(() => {
  navigateTo("/bank/");
}, 2000);
</script>

<template>
  <Loading />
</template>

<style lang="scss" scoped></style>
