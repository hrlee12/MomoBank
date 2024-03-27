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

// 라우터 이름에 따른 타이틀 및 sideMenuActive 상태 매핑
const NO_SIDE_MENU = 0;
const FULL_MENU = 1;
const ONLY_ARROW = 2;
const routerNameToInfoMap = {
  "user-signup": { title: "회원가입", sideMenuActive: NO_SIDE_MENU },
  "user-authenicate": { title: "본인확인", sideMenuActive: NO_SIDE_MENU },
  "user-find-password": { title: "비밀번호찾기", sideMenuActive: NO_SIDE_MENU },

  "bank-account-list": { title: "전체계좌", sideMenuActive: FULL_MENU },
  "bank-group-list": { title: "전체모임", sideMenuActive: FULL_MENU },
  "bank-history": { title: "거래내역", sideMenuActive: FULL_MENU },
  "bank-notice": { title: "알림", sideMenuActive: NO_SIDE_MENU },
  "bank-profile": { title: "마이페이지", sideMenuActive: FULL_MENU },
  "bank-profile-phone-edit": {
    title: "전화번호수정",
    sideMenuActive: NO_SIDE_MENU,
  },
  "bank-profile-password-edit": {
    title: "비밀번호수정",
    sideMenuActive: NO_SIDE_MENU,
  },
  "bank-remit": { title: "계좌개설", sideMenuActive: FULL_MENU },
  user: { title: "", sideMenuActive: ONLY_ARROW },
  "bank-group-create": { title: "모임생성", sideMenuActive: NO_SIDE_MENU },
  "bank-group-create-account-select": {
    title: "계좌선택",
    sideMenuActive: NO_SIDE_MENU,
  },
  "bank-group-create-card-select": {
    title: "카드선택",
    sideMenuActive: NO_SIDE_MENU,
  },
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
  <header :style="{ visibility: isSideMenuActive == 2 ? 'hidden' : 'visible' }">
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
  z-index: 2;

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
