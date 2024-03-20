<script setup>
import { ref } from "vue";
import NoticeHome from "~/components/ui/NoticeHome.vue";

const goBack = () => {
  window.history.back();
};

// 이미지 불러오는 메소드
const getImageUrl = (imageName, idx) => {
  if (idx == 0) return "/icon/" + imageName;
  else if (idx === 1) return "/images/" + imageName;
  else console.log("Image code error");
};

const pageName = ref("전체계좌");
</script>

<template>
  <header>
    <div class="link-container left" @click="goBack">
      <img
        class="double"
        :src="getImageUrl('arrow-icon.png', 0)"
        alt="arrow-icon 화살표 아이콘"
      />
    </div>

    <h1 class="title">{{ pageName }}</h1>

    <NoticeHome />
  </header>
</template>

<style lang="scss" scoped>
@import "~/assets/css/main.scss";

img {
  height: 3.8vh;
}

.double {
  height: 5vh;
}

header {
  position: relative;
  width: 100%;
  height: 8vh;
  vertical-align: middle;
  padding: 1.5vh 1.5vh 1vh 1.5vh;
  display: flex;
  align-items: center;

  .link-container {
    display: flex;
    width: 25%;
  }

  .title {
    width: 50%;
    text-align: center;
  }
}
</style>
