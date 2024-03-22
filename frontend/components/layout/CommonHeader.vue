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

// 라우터 이름에 따른 타이틀 및 sideMenuActive 상태 매핑
const routerNameToInfoMap = {
  "bank-account-list": { title: "전체계좌", sideMenuActive: true },
  "bank-group-list": { title: "전체모임", sideMenuActive: true },
  "bank-history": { title: "거래내역", sideMenuActive: true },
  "bank-notice": { title: "알림", sideMenuActive: false },
  "bank-profile": { title: "마이페이지", sideMenuActive: true },
  "bank-remit": { title: "계좌개설", sideMenuActive: true },
  user: { title: "", sideMenuActive: false },
  "user-signup": { title: "회원가입", sideMenuActive: false },
  "user-authenicate": { title: "본인확인", sideMenuActive: false },
};

const pageTitle = computed(() => {
  const routeInfo = routerNameToInfoMap[router.name];
  return routeInfo ? routeInfo.title : ""; // 라우터 이름에 해당하는 타이틀이 없는 경우 기본 타이틀 반환
});

const isSideMenuActive = computed(() => {
  const routeInfo = routerNameToInfoMap[router.name];
  return routeInfo ? routeInfo.sideMenuActive : false; // sideMenuActive가 1이면 true, 아니면 false 반환
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

    <div v-if="isSideMenuActive" class="link-container">
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
