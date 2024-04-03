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
const NO_HEADER = 2;

// 동적 세그먼트 값을 포함한 라우터 이름을 기반으로 타이틀과 사이드 메뉴 활성화 상태를 결정
const pageTitle = computed(() => {
  // if (number && router.name.startsWith("user-")) {
  //   return { title: "거래내역", sideMenuActive: NO_SIDE_MENU };
  // }
  // 정적 라우터 이름에 대한 설정
  const staticRoutesInfo = {
    "user-signup": { title: "회원가입", sideMenuActive: NO_SIDE_MENU },
    "user-authenicate": { title: "본인확인", sideMenuActive: NO_SIDE_MENU },
    "user-find-password": {
      title: "비밀번호찾기",
      sideMenuActive: NO_SIDE_MENU,
    },

    "bank-account-detail": { title: "거래내역", sideMenuActive: FULL_MENU },
    "bank-account-list": { title: "전체계좌", sideMenuActive: FULL_MENU },
    "bank-account-create": { title: "계좌개설", sideMenuActive: FULL_MENU },
    "bank-account-create-agree": {
      title: "약관동의",
      sideMenuActive: NO_SIDE_MENU,
    },
    "bank-account-create-account-agree": {
      title: "약관동의",
      sideMenuActive: NO_SIDE_MENU,
    },
    "bank-account-create-password-check": {
      title: "계좌 비밀번호 입력",
      sideMenuActive: NO_SIDE_MENU,
    },
    "bank-account-create-survey": {
      title: "카드 설문조사",
      sideMenuActive: NO_SIDE_MENU,
    },
    "bank-account-create-card-select": {
      title: "카드선택",
      sideMenuActive: NO_SIDE_MENU,
    },
    "bank-account-create-card-agree": {
      title: "약관동의",
      sideMenuActive: NO_SIDE_MENU,
    },
    "bank-remit-password-check": {
      title: "계좌 비밀번호",
      sideMenuActive: NO_SIDE_MENU,
    },
    "bank-group-list": { title: "전체모임", sideMenuActive: FULL_MENU },
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
    "bank-remit": { title: "송금", sideMenuActive: NO_SIDE_MENU },
    "bank-remit-money-input": { title: "송금", sideMenuActive: NO_SIDE_MENU },
    "bank-remit-confirm-remit": {
      title: "송금확인",
      sideMenuActive: NO_SIDE_MENU,
    },
    "bank-group-create": { title: "모임생성", sideMenuActive: NO_SIDE_MENU },
    "bank-group-create-account-select": {
      title: "계좌선택",
      sideMenuActive: NO_SIDE_MENU,
    },
    "bank-account-create-password-check": {
      sideMenuActive: NO_SIDE_MENU,
    },
  };

  return (
    staticRoutesInfo[router.name] || { title: "", sideMenuActive: NO_HEADER }
  );
});

const isSideMenuActive = computed(() => pageTitle.value.sideMenuActive);
</script>

<template>
  <header
    :style="{ visibility: isSideMenuActive === 2 ? 'hidden' : 'visible' }"
  >
    <div class="link-container left" @click="goBack">
      <img
        class="double"
        :src="getImageUrl('arrow-icon.png', 0)"
        alt="arrow-icon 화살표 아이콘"
      />
    </div>

    <!-- <h1 class="title">{{ menuStore.menuTitle }}</h1> -->
    <h1 class="title">{{ pageTitle.title }}</h1>

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
