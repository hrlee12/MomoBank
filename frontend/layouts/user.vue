<script setup>
import CommonHeader from "~/components/layout/CommonHeader.vue";

// 이미지 불러오는 메소드
const getImageUrl = (imageName, idx) => {
  if (idx == 0) return "/icon/" + imageName;
  else if (idx === 1) return "/images/" + imageName;
  else console.log("Image code error");
};
</script>

<template>
  <CommonHeader />
  <!-- output the page content -->
  <div class="layout-container">
    <div class="loading-content">
      <img :src="getImageUrl('logo-icon.png', 0)" alt="" class="rotate-image" />

      <div class="text-content">
        <h2>모두가 모이는 뱅킹</h2>
        <h1>모모뱅크</h1>
      </div>
    </div>

    <div class="nuxt-content">
      <NuxtPage />
    </div>
  </div>
</template>

<style lang="scss" scoped>
@import "@/assets/css/main.scss";

.nuxt-content {
  display: flex;
  justify-content: center;
  min-height: 30vh;
}

.loading-content {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 5vh 0 3vh 0;
}

.text-content {
  text-align: center;
  padding: 5%;
}

.rotate-image {
  height: 15vh;
  align-self: center;
}
</style>
