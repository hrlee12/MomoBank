<script setup>
// 이미지 불러오는 메소드
const getImageUrl = (imageName, idx) => {
  if (idx == 0) return "/icon/" + imageName;
  else if (idx === 1) return "/images/" + imageName;
  else console.log("Image code error");
};
</script>

<template>
  <div class="loading-content">
    <img :src="getImageUrl('logo-icon.png', 0)" alt="" />

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
}

img {
  height: 30vh;
  margin-bottom: 10%;
}

.text-content {
  text-align: center;
  padding: 5%;
}
</style>
