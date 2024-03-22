<script setup>
import NoticeHome from "~/components/ui/NoticeHome.vue";
import { useRoute } from "#vue-router";

const goBack = () => {
  window.history.back();
};

// 이미지 불러오는 메소드
const getImageUrl = (imageName, idx) => {
  if (idx == 0) return "/icon/" + imageName;
  else if (idx === 1) return "/images/" + imageName;
  else console.log("Image code error");
};

const router = useRoute();

const menuActive = ref(true);
const sideMenuActive = ref(true);

const pageTitle = computed(() => {
  menuActive.value = true;
  sideMenuActive.value = true;

  if (router.name === "bank-account-list") {
    return "전체계좌";
  } else if (router.name === "bank-group-list") {
    return "전체모임";
  } else if (router.name === "history") {
    return "거래내역";
  } else if (router.name === "bank-notice") {
    sideMenuActive.value = false;
    return "알림";
  } else if (router.name === "bank-profile") {
    return "마이페이지";
  } else if (router.name === "bank-remit") {
    return "계좌개설";
  } else if (router.name === "user") menuActive.value = false;
});
</script>

<template>
  <header :style="{ visibility: menuActive ? 'visible' : 'hidden' }">
    <div class="link-container left" @click="goBack">
      <img
        class="double"
        :src="getImageUrl('arrow-icon.png', 0)"
        alt="arrow-icon 화살표 아이콘"
      />
    </div>

    <!-- <h1 class="title">{{ menuStore.menuTitle }}</h1> -->
    <h1 class="title">{{ pageTitle }}</h1>

    <div v-if="sideMenuActive" class="link-container">
      <NoticeHome />
    </div>
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
  padding: 3vh 1.5vh 1vh 1.5vh;
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
