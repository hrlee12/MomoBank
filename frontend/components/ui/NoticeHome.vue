<script setup>
// 이미지 불러오는 메소드
const getImageUrl = (imageName, idx) => {
  if (idx == 0) return "/icon/" + imageName;
  else if (idx === 1) return "/images/" + imageName;
  else console.log("Image code error");
};
</script>

<template>
  <div class="link-container around">
    <NuxtLink to=""
      ><img class="item" :src="getImageUrl('bell-icon.png', 0)" alt="홈 아이콘"
    /></NuxtLink>
    <NuxtLink to="/bank"
      ><img class="item" :src="getImageUrl('home-icon.png', 0)" alt="홈 아이콘"
    /></NuxtLink>
  </div>
</template>

<style lang="scss" scoped>
@import "@/assets/css/main.scss";

.link-container {
  display: flex;
  width: 25%;
}

img {
  height: 3.8vh;
}

.around {
  justify-content: space-around;
}
</style>
