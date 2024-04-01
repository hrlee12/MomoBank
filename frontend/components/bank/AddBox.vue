<script setup>
// 이미지 불러오는 메소드
const getImageUrl = (imageName, idx) => {
  if (idx == 0) return "/icon/" + imageName;
  else if (idx === 1) return "/images/" + imageName;
  else console.log("Image code error");
};

// props 정의
const props = defineProps({
  to: String,
});
</script>

<template>
  <div class="content">
    <NuxtLink :to="props.to">
      <img :src="getImageUrl('add-icon2.png', 0)" alt="" />
    </NuxtLink>
  </div>
</template>

<style lang="scss" scoped>
@import "~/assets/css/main.scss";
@import "~/assets/css/content.scss";

img {
  height: 3vh;
  align-self: center;
}

.content {
  align-items: center;
  padding: 3% 0;
  opacity: 70%;
}
</style>
